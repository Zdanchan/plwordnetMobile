"use strict";

import {apiConnector} from './api-connector';
import {GraphCreator, consts} from "./graph-creator";
import {Edge} from "./graph-edges-container";

let geoUtils = {};

geoUtils.calculateEllipseY = function(a, b, h, k, x, top){
  if (top) {
    return   k + Math.sqrt(b*b - (1-(x-h)*(x-h))/(a*a));
  } else {
    return   k - Math.sqrt(b*b - (1-(x-h)*(x-h))/(a*a));
  }
};
geoUtils.calculateEllipseX = function(a, b, h, k, y, right){
  if (right){
    return h - Math.sqrt(b*b - (1-(y-k)*(y-k))/(a*a));
  } else {
    return h + Math.sqrt(b*b - (1-(y-k)*(y-k))/(a*a));
  }
};
geoUtils.distributeNode = function(nodeSize, nodeNumber, numberOfAllChildren){
  const avaliableSpace = numberOfAllChildren * nodeSize * 2 ;
  const startPlace = - (avaliableSpace /2);
  const offset =  startPlace + nodeSize * 2 * (nodeNumber + 1) - nodeSize;
  return offset;
};
geoUtils.assignPlace = function(place, masterNode, childNode, cnt, numberOfAllChildren) {
  const consts = GraphCreator.prototype.consts;
  numberOfAllChildren = numberOfAllChildren >= consts.maxDefaultNodesHorizontally ? consts.maxDefaultNodesHorizontally : numberOfAllChildren;
  switch (place) {
    case 'top':
      childNode.x = masterNode.x + geoUtils.distributeNode(consts.nodeWidth + 2, cnt, numberOfAllChildren);
      childNode.y = -200 + geoUtils.calculateEllipseY(8,1,masterNode.x,masterNode.y,childNode.x, true); //masterNode.y - thisGraph.consts.nodeDistance;
      break;
    case 'right':
      childNode.y = masterNode.y + geoUtils.distributeNode(consts.nodeHeight - 15, cnt, numberOfAllChildren);
      childNode.x = 200 + geoUtils.calculateEllipseX(3,1,masterNode.x,masterNode.y,childNode.y, true);  // thisGraph.consts.nodeDistance;
      break;
    case 'bottom':
      childNode.x = masterNode.x + geoUtils.distributeNode(consts.nodeWidth + 10, cnt, numberOfAllChildren);
      childNode.y = masterNode.y + consts.nodeDistance;
      childNode.y = 200 + geoUtils.calculateEllipseY(8,1,masterNode.x,masterNode.y,childNode.x, false);
      break;
    case 'left':
      childNode.x = masterNode.x - consts.nodeDistance;
      childNode.y = masterNode.y + geoUtils.distributeNode(consts.nodeHeight - 15, cnt, numberOfAllChildren);
      childNode.x = -200 + geoUtils.calculateEllipseX(3,1,masterNode.x,masterNode.y,childNode.y, false);
      break;
  }
};

export
const GraphNode = function(json, x, y, parent, graph,expandedTop, expandedRight, expandedBottom, expandedLeft){
  let thisNode = this;
  thisNode.graph = graph;

  if(json){
    thisNode.parentId = parent.id || null;
    thisNode.parent = parent || null;

    json = thisNode.prepareJson(json);

    thisNode.id = json.id;
    thisNode.label = json.label;
    thisNode.partOfSpeechId = json.pos;

    thisNode.assignColorBasedFromPartOfSpeech();

    thisNode.x = x || 0;
    thisNode.y = y || 0;


    thisNode.childrenTop = json.top.expanded.concat(json.top.hidden);
    thisNode.childrenRight = json.right.expanded.concat(json.right.hidden);
    thisNode.childrenBottom = json.bottom.expanded.concat(json.bottom.hidden);
    thisNode.childrenLeft = json.left.expanded.concat(json.left.hidden);

    thisNode.parentRel = json.rel;


    thisNode.childrenTopRef = [];
    thisNode.childrenRightRef= [];
    thisNode.childrenBottomRef= [];
    thisNode.childrenLeftRef = [];

    thisNode.cumulativeNodes = {};
    thisNode.addCumulativeChildren(json);
    thisNode.initChildren(json.top.expanded, json.right.expanded, json.bottom.expanded, json.left.expanded);
  }
  thisNode.selected = false;

  thisNode.expandedTop = expandedTop || false;
  thisNode.expandedRight = expandedRight || false;
  thisNode.expandedLeft = expandedLeft || false;
  thisNode.expandedBottom = expandedBottom || false;


  thisNode.childrenDownloaded = false;
};

GraphNode.prototype.reset = function(){
  const thisNode = this;

  thisNode.expandedTop = false;
  thisNode.expandedRight = false;
  thisNode.expandedLeft = false;
  thisNode.expandedBottom = false;
};

GraphNode.prototype.assignPlace = function(ref, cnt, syblingsCnt){
  const thisNode = this;
  geoUtils.assignPlace(ref, thisNode.parent, thisNode, cnt, syblingsCnt);
};

GraphNode.prototype.initChildren = function(top, right, bottom, left){
  const thisNode = this;

  // todo - check if children not already present
  function createChildren(children, refLong, ref){
    let node;
    // count cumulative nodes as well
    let allChildrenLen = children.length + thisNode[refLong].length;
    for(let i = 0; i < children.length; i++){

      // assumed not child if already on the graph
      node = thisNode.graph.nodes.get(children[i].id);
      if(!node){
        node = new GraphNode(children[i], 0, 0, thisNode, thisNode.graph);
        node.assignPlace(ref, i, allChildrenLen);
        thisNode[refLong].push(node);
      }

    }
    thisNode.childrenDownloaded = true;
  }

  createChildren(top, 'childrenTopRef', 'top');
  createChildren(right, 'childrenRightRef', 'right');
  createChildren(bottom, 'childrenBottomRef', 'bottom');
  createChildren(left, 'childrenLeftRef', 'left');
};

GraphNode.prototype.prepareJson = function(jsonOriginal ){
  function defineUndefined(json, position){
    if (!json[position])
      json[position]= {
        expanded: [],
        hidden: []
      };

    if (!json[position].expanded)
      json[position].expanded = [];

    if (!json[position].hidden)
      json[position].hidden = [];
  }
  defineUndefined(jsonOriginal, 'top');
  defineUndefined(jsonOriginal, 'right');
  defineUndefined(jsonOriginal, 'bottom');
  defineUndefined(jsonOriginal, 'left');

  return jsonOriginal;
};

GraphNode.prototype.addCumulativeChildren = function(json){
  const thisNode = this;

  function addCumumativeNodes(position){
    let node;
    thisNode.cumulativeNodes[position] = null;
    if (json[position].hidden.length > 0 && json[position].expanded.length !== 0){
      node = new GraphNodeCumulator({id:  - json[position].hidden[0].id, type:{color: "green"}, rel: [null, null]}, thisNode, json[position].hidden, position);
      // todo - optimize
      node.assignPlace(position, thisNode['children' + position.charAt(0).toUpperCase() + position.slice(1) +'Ref'].length + 1, thisNode['children' + position.charAt(0).toUpperCase() + position.slice(1) +'Ref'].length - 2);
      thisNode['children' + position.charAt(0).toUpperCase() + position.slice(1) +'Ref'].push(node)
    }
    thisNode.cumulativeNodes[position] = node;
  }
  addCumumativeNodes('top');
  addCumumativeNodes('right');
  addCumumativeNodes('bottom');
  addCumumativeNodes('left');
};

GraphNode.prototype.addChildrenAtPosIfNotParent = function(newNodes, childrenPos){
  let thisNode = this;

  thisNode[childrenPos] = thisNode[childrenPos].concat(
    newNodes.filter(item => {
      return item.id !== thisNode.parentId;
    })
  );
};

GraphNode.prototype.addToCumulativeNode = function(nodes, place){
  const thisNode = this;
  place = ['top', 'right', 'bottom', 'left'][place];

  nodes.forEach(node => {
    thisNode.cumulativeNodes[place].addHiddenNode(node);
  });
};

GraphNode.prototype.updateChildrenPosition = function(place){
  let thisNode = this;
  let placeStr = ['Top', 'Right', 'Bottom', 'Left'][place];


  let nodes = thisNode['children' + placeStr + 'Ref'];

  // hiding nodes if already shown
  let nodesToDel =nodes.slice(5);
  if(nodesToDel.length > 0){
    thisNode.addToCumulativeNode(nodesToDel, place);
  }

  nodes = nodes.slice(0,5);
  thisNode['children' + placeStr + 'Ref'] = nodes;

  nodes.forEach((node, idx) => {
    node.assignPlace(placeStr.toLowerCase(), idx, nodes.length);
  });
};

GraphNode.prototype.updateChildren = function(json, place){
  const thisNode = this;

  if(thisNode.childrenDownloaded)
    return;

  json = thisNode.prepareJson(json);

  thisNode.addChildrenAtPosIfNotParent(json.top.hidden, 'childrenTop');
  thisNode.addChildrenAtPosIfNotParent(json.right.hidden, 'childrenRight');
  thisNode.addChildrenAtPosIfNotParent(json.bottom.hidden, 'childrenBottom');
  thisNode.addChildrenAtPosIfNotParent(json.left.hidden, 'childrenLeft');


  // todo - change this later
  thisNode.childrenTopRef = [];
  thisNode.childrenRightRef= [];
  thisNode.childrenBottomRef= [];
  thisNode.childrenLeftRef = [];

  thisNode.addCumulativeChildren(json);
  thisNode.initChildren(json.top.expanded, json.right.expanded, json.bottom.expanded, json.left.expanded);

  thisNode.expandedTop = (place === 0);
  thisNode.expandedRight = (place === 1);
  thisNode.expandedBottom = (place === 2);
  thisNode.expandedLeft = (place === 3);

  thisNode.childrenDownloaded = true;
};


GraphNode.prototype.markSelected = function(){
  const thisNode = this;
  thisNode.selected = true;
};

GraphNode.prototype.unmarkSelected = function(){
  const thisNode = this;
  thisNode.selected = false;
};

GraphNode.prototype.assignColorBasedFromPartOfSpeech = function () {
  const thisNode = this;
  let color;
  if(!consts.partOfSpeech[thisNode.partOfSpeechId])
    color = '#F0E68C'; // default
  else
    color = consts.partOfSpeech[thisNode.partOfSpeechId].color;

  thisNode.type = {
    color
  }
};

GraphNode.prototype.nodeType = 'ordinary';

GraphNode.prototype.getType = function(){
  return "ordinary";
};

GraphNode.prototype.api = apiConnector;

GraphNode.prototype.consts = {
  DIRECTIONS: {
    TOP: 0,
    RIGHT: 1,
    BOTTOM: 2,
    LEFT: 3
  }
};

GraphNode.prototype.getChildrenWithCallback = function(callback){
  const thisNode = this;

  if(thisNode.childrenDownloaded)
    callback(thisNode.apiData);

  else
    thisNode.api.getGraph(thisNode.id, function(d){
      thisNode.apiData = d;
      callback(d);
    });
};

GraphNode.prototype.expandTopClick = function(allNodes, callback){
  const thisNode = this;
  thisNode.expandedTop = true;

  thisNode.findNewPlace(allNodes);
  thisNode.getChildrenWithCallback(callback);
};

GraphNode.prototype.expandRightClick = function (allNodes, callback) {
    const thisNode = this;
    thisNode.expandedRight = true;

    thisNode.findNewPlace(allNodes);
    thisNode.getChildrenWithCallback(callback);

};

GraphNode.prototype.expandBottomClick = function (allNodes, callback) {
    const thisNode = this;
    thisNode.expandedBottom = true;

    thisNode.findNewPlace(allNodes);
    thisNode.getChildrenWithCallback(callback);
};

GraphNode.prototype.expandLeftClick = function (allNodes, callback) {
    const thisNode = this;
    thisNode.expandedLeft = true;

    thisNode.findNewPlace(allNodes);
    thisNode.getChildrenWithCallback(callback);
};

GraphNode.prototype.moveChildren = function (newPointsOffsetArr) {
    const thisNode = this;
    const children = thisNode.getAllChildrenRef();
    for(let i =0; i< children.length; i++){
        let offset = [children[i].x - newPointsOffsetArr[0], children[i].y - newPointsOffsetArr[1]];
        children[i].assignNewPosition(offset);
        children[i].moveChildren(newPointsOffsetArr);
    }
};

GraphNode.prototype.getChildrenAtPosition = function(pos){
  const thisNode = this;
  switch(pos){
    case 0:
      return thisNode.childrenTopRef;
      break;
    case 1:
      return thisNode.childrenRightRef;
      break;
    case 2:
      return thisNode.childrenBottomRef;
      break;
    case 3:
      return thisNode.childrenLeftRef;
      break;

  }
};
GraphNode.prototype.connectionPointTypes = {
  0: [0,2],
  1: [1,3],
  2: [2,0],
  3: [3,1],
  top: [0,2],
  right: [1,3],
  bottom: [2,0],
  left: [3,1],

};

GraphNode.prototype.getConnectionWithNode = function(node){
  const thisNode = this;

  for(let [i, pos]  of ['childrenTop','childrenRight','childrenBottom', 'childrenLeft'].entries()){
    let child = thisNode[pos].filter(child => child.id === node.id)[0];
    if (child){
      return new Edge(thisNode, node, thisNode.connectionPointTypes[i], child.rel)
    }
  }
  return null;
};

GraphNode.prototype.getAllChildren = function(){
  const thisNode = this;

  return thisNode.childrenTop.concat(
    thisNode.childrenRight,
    thisNode.childrenBottom,
    thisNode.childrenLeft);
};

GraphNode.prototype.getAllChildrenRef= function(){
  const thisNode = this;
  const children = thisNode.childrenTopRef.concat(
      thisNode.childrenRightRef,
      thisNode.childrenBottomRef,
      thisNode.childrenLeftRef);
  return children;
};

GraphNode.prototype.assignNewPosition = function(newPointsArr){
  const thisNode=this;
  thisNode.x = newPointsArr[0];
  thisNode.y = newPointsArr[1];
};

GraphNode.prototype.findNewPlace = function(allNodes){
  const thisNode = this;

  let newPoints = [thisNode.x, thisNode.y];
  let offset = 20;
  let max_iter = 10;
  while(!thisNode.checkIfSpaceInRegion(newPoints, allNodes) && max_iter > 0){
    newPoints = thisNode.calculatePossibleNewPosition(offset);
    offset += 20;
    max_iter--;
  }
  thisNode.moveChildren([thisNode.x - newPoints[0], thisNode.y - newPoints[1]]);
  thisNode.assignNewPosition(newPoints);
  return newPoints;
};

GraphNode.prototype.checkIfSpaceInRegion = function(newPointsArr, allNodes){
  const thisNode = this;

  // TODO this has to be dependent on number of new nodes!
  const paddingVertical = 500;
  const paddingHorizontal = 1000;


  const x = newPointsArr[0];
  const y = newPointsArr[1];

  const children = thisNode.getAllChildrenRef();

  for (let [key, node] of allNodes){
    if(children.indexOf(node) > -1) continue;
    const compareX = node.x;
    const compareY = node.y;

    if(thisNode.expandedTop){
      if(thisNode.checkIfPointInRange(compareX, compareY,
          x - paddingHorizontal /2, x + paddingHorizontal/2,
          y-paddingVertical, y)){
        return false;
      }
    }
    if(thisNode.expandedRight){
      if(thisNode.checkIfPointInRange(compareX, compareY,
          x, x + paddingHorizontal,
          y - paddingVertical /2, y + paddingVertical /2)){
        return false;
      }
    }
    if(thisNode.expandedBottom){
      if(thisNode.checkIfPointInRange(compareX, compareY,
          x - paddingHorizontal /2, x + paddingHorizontal/2,
          y, y + paddingVertical)){
        return false;
      }
    }
    if(thisNode.expandedLeft){
      if(thisNode.checkIfPointInRange(compareX, compareY,
          x - paddingHorizontal, x,
          y - paddingVertical /2, y + paddingVertical /2)){
        return false;
      }
    }
  }
  return true;
};

GraphNode.prototype.checkIfPointInRange = function(x,y,minX, maxX, minY, maxY){
  const resultX = (x > minX && x < maxX),
    resultY = (y > minY && y < maxY);
  return resultX && resultY;
};

GraphNode.prototype.calculatePossibleNewPosition = function(offset){
  const thisNode = this;
  const parent = thisNode.parent;

  const deltaX = parent.x - thisNode.x;
  const deltaY = parent.y - thisNode.y;

  let newX = thisNode.x;
  let newY = thisNode.y;

  // for masterNode
  if(!thisNode.parent.x || !thisNode.parent.y )
    return [newX, newY];

  const ratio = deltaY / deltaX;


  if(deltaX === 0 || Math.abs(deltaX) < 50 ){
      newY = deltaY > 0 ? thisNode.y - offset : thisNode.y + offset;
  } else if( deltaY === 0 && deltaX < 0){
      newX = thisNode.x + offset;
  } else if(deltaY < 0){
    if(ratio < 0){
      newX = thisNode.x - offset;
      newY = thisNode.y - offset * ratio;
    } else {
      newX = thisNode.x + offset;
      newY = thisNode.y + offset * ratio;
    }
  } else {
    if(ratio < 0){
      newX = thisNode.x + offset;
      newY = thisNode.y + offset * ratio;
    } else {
      newX = thisNode.x - offset;
      newY = thisNode.y - offset * ratio;
    }
  }

  return [newX, newY];
};

GraphNode.prototype.isNodeCumulator = function(){
  return false;
};

GraphNode.prototype.expandTriangleClick = function(graph, triangleHandle, place){
  const thisNode = this;
  const expandedIndicator = ['expandedTop', 'expandedRight', 'expandedBottom', 'expandedLeft'][place];
  const expandedFunction = ['expandTopClick', 'expandRightClick', 'expandBottomClick', 'expandLeftClick'][place];
  const children = ['childrenTop', 'childrenRight', 'childrenBottom', 'childrenLeft'][place];

  // if node at given position is not expanded
  if(!thisNode[expandedIndicator]){
    thisNode[expandedFunction](graph.nodes, function (data) {
      // if(!thisNode.childrenDownloaded)
      thisNode.updateChildren(data, place);

      thisNode.updateChildrenPosition(place);
      graph.updateWithChildrenNodes(thisNode);
      d3.select(triangleHandle).classed("expanded", true);
    });
  }
  else {
    thisNode[expandedIndicator] = false;

    graph.removeNodeChildren(thisNode, place);
    d3.select(triangleHandle).classed("expanded", false);
  }

};


const GraphNodeCumulator = function (json, parent, hiddenNodes, position) {
  const thisNode = this;

  GraphNode.call(this, json, 100, 100, parent, parent.graph);

  thisNode.hiddenNodes = hiddenNodes || [];
  thisNode.positionRelativeToParent = position;
  thisNode.label = thisNode.hiddenNodes.length;
  thisNode.hiddenButVisible = 0;

  thisNode.childrenTop = [];
  thisNode.childrenRight= [];
  thisNode.childrenBottom= [];
  thisNode.childrenLeft= [];
};

GraphNodeCumulator.prototype = new GraphNode();
GraphNodeCumulator.prototype.constructor = GraphNodeCumulator;
GraphNodeCumulator.prototype.nodeType = 'cumulator';

GraphNodeCumulator.prototype.getType = function(){
  return "cumulator";
};

GraphNodeCumulator.prototype.getHiddenNodes = function(){
  const thisNode = this;
  return thisNode.hiddenNodes;
};

GraphNodeCumulator.prototype.updateCount = function(){
  const thisNode = this;
  thisNode.label = thisNode.hiddenNodes.length - thisNode.hiddenButVisible;
};

GraphNodeCumulator.prototype.decrementTitle = function(){
  const thisNode = this;

  thisNode.label -= 1;

  if(thisNode.label === 0){
    this.graph.removeNode(thisNode);
  }
};

GraphNodeCumulator.prototype.expandHiddenNode = function(hiddenRef, cumulatorParent){
  const thisNode = this;

  thisNode.graph.hiddenList.style("display", "none");

  thisNode.api.getGraph(hiddenRef.id, function(data){
    data.rel = hiddenRef.rel;
    let newNode = new GraphNode(data, thisNode.x + 100 + Math.floor((Math.random() * 20) - 10), thisNode.y + 100 + + Math.floor((Math.random() * 20) - 10), thisNode.parent, thisNode.graph);
    newNode.childrenDownloaded = true;

    thisNode.parent['children'
    + thisNode.positionRelativeToParent.charAt(0).toUpperCase() + thisNode.positionRelativeToParent.slice(1)
    + 'Ref'].push(newNode);

    thisNode.graph.nodes.set(newNode.id, newNode);
    let connection = thisNode.connectionPointTypes[thisNode.positionRelativeToParent];

    thisNode.hiddenNodes.splice(thisNode.hiddenNodes.indexOf(hiddenRef), 1);

    thisNode.decrementTitle();

    thisNode.graph.edges.add(new Edge(thisNode.parent, newNode, connection, newNode.parentRel));
    thisNode.graph.addEdgesBetweenNewNodes([newNode]);
    thisNode.graph.updateGraph();
  });
};

GraphNodeCumulator.prototype.addOnclickToCreatedNodes = function(graph, hiddenNodesIds, hiddenNodesRef){
  const thisNode = this;

  for(let i = 0; i < hiddenNodesIds.length; i++){
    (function(index) {
      let nodeId = hiddenNodesIds[index];
      d3.select(nodeId).on('click', function () {
        thisNode.expandHiddenNode(hiddenNodesRef[index], thisNode);
      })
    })(i);
  }
};

GraphNodeCumulator.prototype.isNodeCumulator = function(){
  return true;
};

GraphNodeCumulator.prototype.getHiddenListHtml = function(){
  const thisNode = this;
  let ret = {
    html: "",
    createdIds: [],
    hiddenRef: []
  };
  ret.html = "<ul class='hidden-nodes-list'>";

  thisNode.hiddenButVisible = 0;
  thisNode.hiddenNodes.forEach((n, i) => {
    if(thisNode.graph.nodes.has(n.id)){
      thisNode.hiddenButVisible++;
      return;
    }

    ret.html += "\n<li id='hidden-"+n.id+"'>" + n.label + "</li>";
    ret.createdIds.push("#hidden-"+n.id);
    ret.hiddenRef.push(thisNode.hiddenNodes[i]);
  });

  ret.html += "\n</ul>";
  thisNode.updateCount();
  return ret;
};

GraphNodeCumulator.prototype.addHiddenNode = function(node){
  const thisNode = this;
  thisNode.hiddenNodes.push({
    id: node.id,
    label: node.label,
    rel: node.parentRel,
    pos: node.partOfSpeechId,
  });
  thisNode.label += 1;
};
