"use strict";

import {apiConnector} from './api-connector.js';

export
function Edge(sourceNode, targetNode, connectionType, relationText, color, dotted){
  this.connectionPoints = connectionType;

  this.pathTextSrc = relationText[0];
  this.pathTextTarget = relationText[1];

  this.color = color || '#000';
  this.dotted = dotted || false;

  this.source = sourceNode;
  this.target = targetNode;

  this.id = this.getUniqueIdentifier();

  // console.log(this);
  this.initColor();
  this.initDotted();
}

Edge.prototype.api = apiConnector; // new ApiConnector();

Edge.prototype.settings = apiConnector.getSettings();

Edge.prototype.initColor = function(){
  const self = this;

  if( !self.pathTextSrc && !self.pathTextTarget)
    return;

  const callback = (settings) => {
    const rel = settings['relations'][self.pathTextSrc] || settings['relations'][self.pathTextTarget];
    try{
      self.color = rel.color;
    }
    catch (err){
      console.log(err);
      console.log(self);
    }

  };

  if(self.api.hasInCache(self.api.entryPoints.settings)){
    callback(self.api.getFromCache(self.api.entryPoints.settings));
  } else {
    self.settings.then( settings => {
      callback(settings);
    });
  }

};

Edge.prototype.initDotted = function(){
  const self = this;

  if( !self.pathTextSrc && !self.pathTextTarget)
    return;

  const callback = (settings) => {
    const rel = settings['relations'][self.pathTextSrc] || settings['relations'][self.pathTextTarget];
    try{
      self.dotted = rel.dotted;
    }
    catch (err){
      console.log(err);
      console.log(self);
    }
  };

  if(self.api.hasInCache(self.api.entryPoints.settings)){
    callback(self.api.getFromCache(self.api.entryPoints.settings));
  } else {
    self.settings.then( settings => {
      callback(settings);
    });
  }

};

Edge.prototype.getUniqueIdentifier = function(){
  let self = this;

  if(self.source.id < self.target.id)
    return this.source.id + '|' + this.target.id;

  return this.target.id + '|' + this.source.id;
};

/**
 * @constructor
 */
export
function EdgeContainer() {

  this.map = new Map();
  this[Symbol.iterator] = this.values;
}

EdgeContainer.prototype.add = function(edge){
  let self = this;
  if(edge)
    self.map.set(edge.id, edge);
};

EdgeContainer.prototype.values = function(){
  let self = this;
  return self.map.values();
};

EdgeContainer.prototype.deleteNodeEdges = function(node){
  let self = this;
  self.map.forEach(edge => {
    if(edge.source === node || edge.target === node ){
      self.map.delete(edge.id);
    }
  });
};
