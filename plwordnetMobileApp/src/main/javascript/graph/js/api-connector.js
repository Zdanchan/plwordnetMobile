"use strict";

// used for passing objects
class Deferred {
  constructor() {
    this.promise = new Promise((resolve, reject)=> {
      this.reject = reject;
      this.resolve = resolve
    })
  }
}

const ApiConnector = function(lang){
  const self = this;

  self.url = "http://graph-slowosiec.clarin-pl.eu/wordnetloom/resources/";
  self.cache = new Map();

  self.possibleLangs = ['pl', 'en'];
  self.lang = lang || 'pl';
};

ApiConnector.prototype.setLang = function(lang){
  const self = this;

  if(self.possibleLangs.indexOf(lang) < 0){
    console.error('Err, Language can only be chosen to be "pl" or "en"');
    return;
  }

  if(self.lang !== lang){
    self.cache = new Map();
    self.lang = lang;
  }
};

ApiConnector.prototype.entryPoints = {
  settings: 'settings/',
  graph: "graphs/synsets/{id}",
  synsetFromSense: "senses/{id}/synset",
  synsetFromLemma: "synsets/search?lemma={lemma}",
  relationTypes: "relation-types/{id}"
};

ApiConnector.prototype._getJson = function(url, callback){
  const self = this;

  // return promise, no callback
  if(!callback)
    return self._getJsonPromise(url);

  if(self.cache.has(url)){
    callback(self.cache.get(url));
  }

  else
    d3.request(self.url + url)
      .header("Content-Type", "application/json")
      .header("Accept-Language", self.lang)
      .get(
        (status, data) => {
          data = JSON.parse(data.response);
          self.cache.set(url, data);
          callback(data);
        });
};

ApiConnector.prototype._getJsonPromise = function(url){
  const self = this;

  let deferredRequest = new Deferred();

  if(self.cache.has(url)){
    deferredRequest.resolve(self.cache.get(url));
  }

  else{
    d3.request(self.url + url)
      .header("Content-Type", "application/json")
      .header("Accept-Language", self.lang)
      .get(
        (status, data) => {
          data = JSON.parse(data.response);
          self.cache.set(url, data);
          deferredRequest.resolve(data);
        });
  }
  return deferredRequest.promise;
};

ApiConnector.prototype.getRelationTypes = function(id, callback){
  const self = this;
  id = id || '';
  return self._getJson(self.entryPoints.relationTypes.replace('{id}', id), callback);

};

ApiConnector.prototype.getSettings = function(callback){
  const self = this;

  return self._getJson(self.entryPoints.settings, callback);
};

ApiConnector.prototype.hasInCache = function(entryPoint){
  const self = this;
  return self.cache.has(entryPoint);
};

ApiConnector.prototype.getFromCache = function(entryPoint){
  const self = this;

  if(self.cache.has(entryPoint))
    return self.cache.get(entryPoint);

  return null;
};

ApiConnector.prototype.getGraph = function(id, callback){
  const self = this;

  self._getJson(self.entryPoints.graph.replace('{id}', id), function(json){
    if(json)
      callback(json);
    else
      callback(null);
  });

};

ApiConnector.prototype.getSynsetFromSenseId = function(id, given_callback){
  const self = this;

  const inner_callback = function (d) {
    given_callback(d)
  };

  d3.json(self.url + self.entryPoints.synsetFromSense.replace('{id}', id), function(status, json){
    if(json){
      inner_callback(json);
    } else {
      inner_callback(null);
    }
  });
};


ApiConnector.prototype.getSensesFromLemma = function(lemma, callback){
  const self = this;
  d3.json(self.url + self.entryPoints.synsetFromLemma.replace('{lemma}', lemma), function (status, json) {
    if(json){
      callback(json);
    } else {
      callback(null);
    }
  });
};

export
const apiConnector = new ApiConnector();
