  "use strict";

  import {apiConnector} from './api-connector';
  import {Edge, EdgeContainer} from "./graph-edges-container";
  import {GraphNode} from "./graph-node";
  import {saveAs} from './FileSaver.min'

  const settings = {
    appendElSpec: ".inner-graph-container",
    sensesListId: ".possible-senses",
    loadingAnimation: ".graph-loader"
  };

  export
  const consts = {
    partOfSpeech:{
      1: {
        color: "#FED25C"
      },
      2: {
        color: "#ABFFAE"
      },
      3: {
        color: "#ABFFAE"
      },
      4: {
        color: "#ACFFEA"
      }
    }
  };

  // define graphcreator object
  export
  const GraphCreator = function(containerId, showSearchBox, width, height) {
    const thisGraph = this;

    thisGraph.width = width;
    thisGraph.height = height;

    const toolboxHtml = '<div id="toolbox">' +
      '<form id="inspected-word-form">' +
      '<button type="submit" value="Submit" id="inspected-word-btn">Szukaj</button>' +
      '<input type="text" id="inspected-word" placeholder="Słowo którego szukasz&hellip;">' +
      '</form>' +
      '<ul class="possible-senses" style="display: none"></ul>' +
      '</div>';


    const downloadBtnHtml = '<button id="download-picture"></button>';

    let initHtml ='<div class="graph-loader" style="display: none"></div><div class="inner-graph-container"></div>';

    if(showSearchBox){
      initHtml += toolboxHtml;
    }
    initHtml += downloadBtnHtml;
    const graphContainer = d3.select('#'+containerId).html(initHtml);
    thisGraph.container = graphContainer;

    const svg = graphContainer.select(settings.appendElSpec).append("svg")
      .attr("width", width)
      .attr("height", height)
      .attr('id', 'graph-svg');

    thisGraph.hiddenList = d3.select(settings.appendElSpec).append("div")
      .attr('pointer-events', 'none')
      .attr("class", "hidden-list tooltip")
      .style("opacity", 1)
      .html("FIRST LINE <br> SECOND LINE")
      .style("display", "none");

    thisGraph.sensesList = d3.select(settings.sensesListId);

    thisGraph.tooltip = d3.select(settings.appendElSpec).append("div")
      .attr("id", "node-tooltip")
      .style("opacity", 0);

    thisGraph.loadingAnimationHandle = d3.select(settings.loadingAnimation)
      .style("display", "none");

    thisGraph.idct = 0;

    thisGraph.nodes = new Map();
    thisGraph.edges = new EdgeContainer();

    thisGraph.transformed = {
      x: 0,
      y: 0,
      k: 1
    };

    thisGraph.state = {
      selectedNode: null,
      selectedEdge: null,
      mouseDownNode: null,
      mouseDownLink: null,
      justDragged: false,
      justScaleTransGraph: false,
      lastKeyDown: -1,
      shiftNodeDrag: false,
      selectedText: null,
      brushSelect: false
    };
    // TODO move to one file
    // thisGraph.relations = new Relations();
    thisGraph.api = apiConnector;// new ApiConnector();

    thisGraph.api.getSettings(settings => {
      thisGraph.settings = settings;
      consts.partOfSpeech = settings.partsOfSpeech;
    });

    // define arrow markers for graph links
    const defs = svg.append('svg:defs');
    defs.append('svg:marker')
      .attr('id', 'end-arrow')
      .attr('viewBox', '0 -5 10 10')
      .attr('refX', "8")
      .attr('markerWidth', 10)
      .attr('markerHeight', 10)
      .attr('orient', 'auto')
      .append('svg:path')
      .attr('d', 'M0,-5L10,0L0,5L5,0');

    // define starting arrow
    defs.append('svg:marker')
      .attr('id', 'start-arrow')
      .attr('viewBox', '0 0 10 10')
      .attr('refX', 2)
      .attr('refY', 5)
      .attr('markerWidth', 10)
      .attr('markerHeight', 10)
      .attr('orient', 'auto')
      .append('svg:path')
      .attr('d', 'M0,5L10,0L5,5L10,10'); //todo - make pointy

    thisGraph.svg = svg;

    thisGraph.svgOuter = svg.append("g")
      .classed("outer-zoom", true)
      .attr("transform", "translate(0,0) scale(1)");


    // thisGraph.svgG = svg.append("g")
    thisGraph.svgG = thisGraph.svgOuter.append("g")
      .classed(thisGraph.consts.graphClass, true)
      .attr('id', 'wordnet-graph-g-element');

    const svgG = thisGraph.svgG;

    // svg nodes and edges
    thisGraph.paths = svgG.append("g").attr("id", "all-paths-id").selectAll("g");
    thisGraph.pathsText = svgG.append("g").attr("id", "all-paths-text-id").selectAll("g");
    thisGraph.boats = svgG.append("g").attr("id", "all-boats-id").selectAll("g");

    thisGraph.drag = d3.drag()
      .subject(function(d) {
        thisGraph.range.x.max = Math.max(d.x, thisGraph.range.x.max);
        thisGraph.range.y.max = Math.max(d.y, thisGraph.range.y.max);
        thisGraph.range.x.min = Math.min(d.x, thisGraph.range.x.min);
        thisGraph.range.y.min = Math.min(d.y, thisGraph.range.y.min);
        return {
          x: d.x,
          y: d.y
        };
      })
      .on("drag", function(args) {
        thisGraph.state.justDragged = true;
        thisGraph.dragmove.call(thisGraph, args);
      });

    // listen for key events
    d3.select(window).on("keydown", function() {
        thisGraph.svgKeyDown.call(thisGraph);
      })
      .on("keyup", function() {
        thisGraph.svgKeyUp.call(thisGraph);
      });
    svg.on("mousedown", function(d) {
      thisGraph.svgMouseDown.call(thisGraph, d);
    });
    svg.on("mouseup", function(d) {
      thisGraph.svgMouseUp.call(thisGraph, d);
    });

    // listen for dragging
    const dragSvg = d3.zoom()
      .on("zoom", function() {
        if (d3.event.sourceEvent.shiftKey) {
          // TODO  the internal d3 state is still changing
          return false;
        }
        else {
          thisGraph.zoomed.call(thisGraph);
        }
        return true;
      })
      .on("start", function() {
        var ael = d3.select("#" + thisGraph.consts.activeEditId).node();
        if (ael) {
          ael.blur();
        }
        if (!d3.event.sourceEvent.shiftKey) d3.select('body').style("cursor", "move");
      })
      .on("end", function() {
        d3.select('body').style("cursor", "auto");
      });

    // console.log(dragSvg);
    thisGraph.zoom = dragSvg;
    svg.call(dragSvg).on("dblclick.zoom", null);

    // listen for resize
    window.onresize = function() {
      thisGraph.updateWindow(svg);
    };


    const brushed = function(){
      thisGraph.brushed();
    };
    thisGraph.brush = d3.brush();
    thisGraph.brush.graph = this;
    thisGraph.brush.on("brush", brushed);

    // searching for word
    d3.select('#inspected-word-form').on('submit', function(){
      d3.event.preventDefault();

      console.log('on submit');

      let input = d3.select("#inspected-word");
      thisGraph.loadNewWord(input.node().value);
    });

    // handle download picture
    d3.select('#download-picture').on('click', function(){
      thisGraph.exportVisualization();
    });

    thisGraph.range = {
      x:{
        min: Number.MAX_SAFE_INTEGER,
        max: Number.MIN_SAFE_INTEGER
      },
      y:{
        min: Number.MAX_SAFE_INTEGER,
        max: Number.MIN_SAFE_INTEGER
      }
    };
  };

  /**
   * Enable minimap preview
   * @param scale - optional minimap scale
   * @param parent -optional html element to attach/
   * @param top - offset top
   * @param right - offset right
   * @param bottom - offset bottom
   * @param left - offset left
   */
  GraphCreator.prototype.showMiniMap = function(scale, parent,top, right, bottom, left){
    const thisGraph = this;
    scale = scale || .25;
    parent = parent || thisGraph.container;
    if(parent !== thisGraph.container)
      parent = d3.select(parent);

    if(top && bottom)
      bottom = null;

    if(top === null && bottom === null)
      top = 0;

    if(right && left)
      left = null;

    if(right === null && left === null)
      left = 0;

    let host = thisGraph;
    let target = d3.select('#graph-svg');

    let minimapScale = scale;
    let minimapPadding = 10;

    thisGraph.minimap = d3.minimap()
      .host(host)
      .target(target)
      .targetShadowId('#all-boats-id')
      .targetShadowId('#all-paths-id')
      .minimapScale(minimapScale)
      .x(0)
      .y(0);

    let svg = parent //d3.select(document.body)
      .append('svg')
      .attr("class", "svg canvas")
      .attr("width", thisGraph.width * scale)
      .attr("height", thisGraph.height * scale)
      .attr("shape-rendering", "auto")
      .style('position', 'absolute');

    if(top !== null && top !== undefined) svg.style('top', top);
    if(right !== null && right !== undefined) svg.style('right', right);
    if(bottom !== null && bottom !== undefined) svg.style('bottom', bottom);
    if(left !== null && left !== undefined) svg.style('left', left);

    svg.call(thisGraph.minimap);
    thisGraph.minimap.render();

  };

  /***********
      MINIMAP FUNCTIONS
   */
  GraphCreator.prototype.getHeight = function(){
    const thisGraph = this;
    return thisGraph.height;
  };

  GraphCreator.prototype.getWidth = function () {
    const thisGraph = this;
    return thisGraph.width;
  };

  GraphCreator.prototype.update = function(minimapZoomTransform) {
    const thisGraph = this;
    thisGraph.zoom.transform(thisGraph.svg , minimapZoomTransform);
    // update the '__zoom' property with the new transform on the rootGroup which is where the zoomBehavior stores it since it was the
    // call target during initialization
    thisGraph.svg.property("__zoom", minimapZoomTransform);

    // thisGraph.updateCanvasZoomExtents();
  };


  /***********
   MINIMAP FUNCTIONS
   */
  GraphCreator.prototype.updateCanvasZoomExtents = function() {
    const thisGraph = this;

    var scale = thisGraph.svg.property("__zoom").k;
    var targetWidth = thisGraph.svg.attr("width");//thisGraph.width;//svgWidth;
    var targetHeight = thisGraph.svg.attr("height");//svgHeight;
    var viewportWidth = thisGraph.width; //width;
    var viewportHeight = thisGraph.height //height;
    thisGraph.zoom.translateExtent([
      [-viewportWidth/scale, -viewportHeight/scale],
      [(viewportWidth/scale + targetWidth), (viewportHeight/scale + targetHeight)]
    ]);
  };

  GraphCreator.prototype.showLoadingAnimation = function () {
    const thisGraph = this;
    thisGraph.loadingAnimationHandle.style("display", "block");
  };

  GraphCreator.prototype.hideLoadingAnimation = function () {
    const thisGraph = this;
    thisGraph.loadingAnimationHandle.style("display", "none");
  };

  GraphCreator.prototype.listPossibleSenses = function(senses){
    const thisGraph = this;
    thisGraph.hideLoadingAnimation();
    if(senses){
      const callback = function(d){
        if(d){
         thisGraph.initializeFromSynsetId(d.id);
        }
      };
      if(senses.length ===0){
        thisGraph.sensesList
          .style('display', 'block')
          .html("") // clear list
          .html("<li>nic nie znaleziono</li>");
      }
      // if only one sense found, display it immediately
      else if(senses.length === 1){
        thisGraph.api.getSynsetFromSenseId(senses[0].id, callback);
      }
      // list possible options
      else {
        let listHtml = "";
        for(let i = 0; i < senses.length; i++){
          listHtml += '<li class="hidden-list-item" data-sense-id="'+senses[i].id+'"'+">"+senses[i].lemma.word+"</li>\n";
        }
        thisGraph.sensesList
          .style('display', 'block')
          .html("") // clear list
          .html(listHtml);

        d3.selectAll(".hidden-list-item")
          .on('click', function(){
            let senseId = d3.select(this).attr("data-sense-id");
            thisGraph.api.getSynsetFromSenseId(senseId, callback);
          });
      }
    }
  };

  GraphCreator.prototype.loadNewWord = function(word){
    const thisGraph = this;
    thisGraph.showLoadingAnimation();
    // pass listPossibleSenses as callback
    thisGraph.api.getSensesFromLemma(word, thisGraph.listPossibleSenses.bind(thisGraph));
  };

  GraphCreator.prototype.initFromJson = function(jsonObj){
    const thisGraph = this;
    try {

      thisGraph.deleteGraph(true);

      const xLoc = thisGraph.width / 2 - 25,
          yLoc = thisGraph.height/2 - 100;

      const masterNode = new GraphNode(jsonObj, xLoc, yLoc, {}, thisGraph, true, true, true, true);
      masterNode.childrenDownloaded = true;

      thisGraph.nodes.set(masterNode.id, masterNode);
      thisGraph.updateWithChildrenNodes(masterNode);

    }
    catch (err) {
      console.log(err);
      window.alert("Error parsing json\nerror message: " + err.message);
    }

  };

  GraphCreator.prototype.edgeNotExisting = function(first, second, additionalArrEdges){
    const thisGraph = this;

    for(let i=0; i< thisGraph.edges.length; i++){
      let edge = thisGraph.edges[i];
      if(edge.source === first && edge.target === second)
        return false;
      if(edge.source === second && edge.target === first)
        return false;
    }
    if (additionalArrEdges !== undefined){
      for(i=0; i < additionalArrEdges.length; i++){
        let edge = additionalArrEdges[i];
        if(edge.source === first && edge.target === second)
          return false;
        if(edge.source === second && edge.target === first)
          return false;
      }
    }
    return true;
  };

  GraphCreator.prototype.addEdgesBetweenNewNodes = function(newNodes){
    const thisGraph = this;


    let crossReference = function(nodeA, nodeB){
      thisGraph.edges.add(nodeA.getConnectionWithNode(nodeB));
      thisGraph.edges.add(nodeB.getConnectionWithNode(nodeA));
    };

    const checkBetweenNewNodes = function(nodes){
      nodes.forEach(a => {
        nodes.forEach( b =>{
          if(a !== b)
              crossReference(a,b);
        });
      })
    };
    const checkBetweenExistingAndNewNodes = function(nodes){
      thisGraph.nodes.forEach(a => {
        nodes.forEach( b =>{
          if(a !== b)
            crossReference(a,b);
        });
      })
    };
    checkBetweenNewNodes(newNodes);
    checkBetweenExistingAndNewNodes(newNodes);
  };

  GraphCreator.prototype.updateWithChildrenNodes = function(masterNode){
    const thisGraph = this;

    let newNodes = [];
    function pushNodesAtPosition(condition, position, connectionType){
      if(!condition)
        return;

      masterNode[position].forEach((node, i) => {
        node = masterNode[position][i];

        if(!thisGraph.nodes.has(node.id)){
          thisGraph.nodes.set(node.id, node);
          newNodes.push(node);
        }
        if (node.isNodeCumulator())
          thisGraph.edges.add(new Edge(masterNode, node, connectionType, node.parentRel));
      });
    }
    pushNodesAtPosition(masterNode.expandedTop, 'childrenTopRef', [0,2]);
    pushNodesAtPosition(masterNode.expandedRight,'childrenRightRef', [1,3]);
    pushNodesAtPosition(masterNode.expandedBottom,'childrenBottomRef', [2,0]);
    pushNodesAtPosition(masterNode.expandedLeft,'childrenLeftRef', [3,1]);

    thisGraph.addEdgesBetweenNewNodes(newNodes);
    thisGraph.updateGraph();
  };

  GraphCreator.prototype.consts = {
    selectedClass: "selected",
    connectClass: "connect-node",
    boatGClass: "conceptG",
    graphClass: "graph",
    activeEditId: "active-editing",
    BACKSPACE_KEY: 8,
    DELETE_KEY: 46,
    ENTER_KEY: 13,
    CTRL_KEY: 17,
    nodeRadius: 50,
    nodeDistance: 225,
    nodeHeight: 35,
    nodeWidth: 50,
    maxDefaultNodesVertically: 6,
    maxDefaultNodesHorizontally: 6
  };

  GraphCreator.prototype.showBrush = function(){
    const thisGraph = this;
    thisGraph.state.brushSelect = true;
    if(!thisGraph.brushHandle){
      thisGraph.brushHandle =  thisGraph.svg.append("g")
        .attr("class", "brush")
        .call(thisGraph.brush);
    }
  };

  GraphCreator.prototype.destroyBrush = function(){
    const thisGraph = this;
    if(thisGraph.brushHandle){
      thisGraph.brushHandle.remove();
      thisGraph.brushHandle = null;
    }

  };

  GraphCreator.prototype.exitBrushMode = function(){
    const thisGraph = this;
    if(thisGraph.state.brushSelect){
      thisGraph.unmarkAllNodes();
      thisGraph.state.brushSelect = false;
    }
  };

  GraphCreator.prototype.unmarkAllNodes = function(){
    const thisGraph = this;
    thisGraph.nodes.forEach(function(node){
      node.unmarkSelected();
    });
    thisGraph.updateGraph();
  };

  GraphCreator.prototype.getPointInvertedTranslation = function(origin, translate, scale){
    return (origin - translate * (1 - scale))/scale - translate;
  };

  GraphCreator.prototype.getPointTransform = function(origin, translate, scale){
    return ((origin + translate) * scale + translate *(1-scale));
  };

  GraphCreator.prototype.getPointTransformX = function(originalX){
    const thisGraph = this;
    return thisGraph.getPointTransform(originalX, thisGraph.transformed.x, thisGraph.transformed.k);
  };
  GraphCreator.prototype.getPointTransformY = function(originalY){
    const thisGraph = this;
    return thisGraph.getPointTransform(originalY, thisGraph.transformed.y, thisGraph.transformed.k);
  };


  GraphCreator.prototype.markNodesSelectedInRange = function(range){
    const thisGraph = this;
    let x1 = range[0][0],
      x2 = range[1][0],
      y1 = range[0][1],
      y2 = range[1][1];


    const xTrans = thisGraph.transformed.x;
    const yTrans = thisGraph.transformed.y;
    const s = thisGraph.transformed.k;

    x1 = thisGraph.getPointInvertedTranslation(x1, xTrans, s);
    x2 = thisGraph.getPointInvertedTranslation(x2, xTrans, s);

    y1 = thisGraph.getPointInvertedTranslation(y1, yTrans, s);
    y2 = thisGraph.getPointInvertedTranslation(y2, yTrans, s);

    thisGraph.nodes.forEach( function(node){
      if( node.x > x1 && node.x < x2 && node.y > y1 && node.y < y2){
      node.markSelected();
      } else{
        node.unmarkSelected();
      }
    });
    thisGraph.updateGraph();
  };

  GraphCreator.prototype.brushed = function(){
    const thisGraph = this;

    const s = d3.event.selection;
    if (s === null) {
      thisGraph.unmarkAllNodes();

    } else {
      thisGraph.markNodesSelectedInRange(s);
    }

  };
  /* PROTOTYPE FUNCTIONS */

  GraphCreator.prototype.dragmove = function(d) {
    const thisGraph = this;

    if (thisGraph.state.shiftNodeDrag) {
      thisGraph.dragLine.attr('d', 'M' + d.x + ',' + d.y + 'L' + d3.mouse(thisGraph.svgG.node())[0] + ',' + d3.mouse(this.svgG.node())[1]);
    }
    else {
    if(!d.selected){ // if not selected node, reset state
      thisGraph.exitBrushMode();
    }
    if (!thisGraph.state.brushSelect) {
      d.x += d3.event.dx;
      d.y += d3.event.dy;
    } else{

      thisGraph.nodes.forEach(function(node){
        if (node.selected){
          node.x += d3.event.dx;
          node.y += d3.event.dy;
        }
      });
     }
     thisGraph.updateGraph();
    }
  };

  GraphCreator.prototype.deleteGraph = function(skipPrompt) {
    const thisGraph = this;
    let doDelete = true;
    if (!skipPrompt) {
      doDelete = window.confirm("Press OK to delete this graph");
    }
    if (doDelete) {
      thisGraph.nodes = new Map();//[];
      thisGraph.edges = new EdgeContainer(); //[];
      thisGraph.resetZoom();
    }
  };

  /* select all text in element: taken from http://stackoverflow.com/questions/6139107/programatically-select-text-in-a-contenteditable-html-element */
  GraphCreator.prototype.selectElementContents = function(el) {
    var range = document.createRange();
    range.selectNodeContents(el);
    var sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(range);
  };

  GraphCreator.prototype.getNodeTitle = function(gEl, title) {
    gEl.append("text")
    .attr("text-anchor", "middle")
    .attr("font-size", "10px")
    .text(title.length < 18 ? title : String(title).slice(0,15) + "...")
    .attr("dy", "3");
  };


  // remove edges associated with a node
  GraphCreator.prototype.spliceLinksForNode = function(node) {
    const thisGraph = this,
      toSplice = thisGraph.edges.filter(function(l) {
        return (l.source === node || l.target === node);
      });
    toSplice.map(function(l) {
      thisGraph.edges.splice(thisGraph.edges.indexOf(l), 1);
    });
  };


  // mousedown on main svg
  GraphCreator.prototype.svgMouseDown = function() {
    this.state.graphMouseDown = true;
    this.state.brushSelect = false;
    this.sensesList.style('display', 'none');
    this.unmarkAllNodes();
  };

  // mouseup on main svg
  GraphCreator.prototype.svgMouseUp = function() {
    const thisGraph = this,
      state = thisGraph.state;
    if (state.justScaleTransGraph) {
      // dragged not clicked
      state.justScaleTransGraph = false;
    }

    else if (state.graphMouseDown && d3.event.shiftKey) {
      // clicked not dragged from svg
      const xycoords = d3.mouse(thisGraph.svgG.node()),
        d = {
          id: thisGraph.idct++,
          title: consts.defaultTitle,
          x: xycoords[0],
          y: xycoords[1]
        };
      thisGraph.nodes.push(d);
      thisGraph.updateGraph();
    }
    else if (state.shiftNodeDrag) {
      // dragged from node
      state.shiftNodeDrag = false;
      thisGraph.dragLine.classed("hidden", true);
    }
    state.graphMouseDown = false;
  };

  // keydown on main svg
  GraphCreator.prototype.svgKeyDown = function() {
    const thisGraph = this,
      state = thisGraph.state,
      consts = thisGraph.consts;
    // make sure repeated key presses don't register for each keydown
    if (state.lastKeyDown !== -1) return;

    state.lastKeyDown = d3.event.keyCode;
    const selectedNode = state.selectedNode,
      selectedEdge = state.selectedEdge;

    switch (d3.event.keyCode) {
      case consts.BACKSPACE_KEY:
        break;
      case consts.CTRL_KEY:
        thisGraph.showBrush();
        break;
      case consts.DELETE_KEY:
        if (selectedNode) {
          thisGraph.nodes.splice(thisGraph.nodes.indexOf(selectedNode), 1);
          thisGraph.spliceLinksForNode(selectedNode);
          state.selectedNode = null;
          thisGraph.updateGraph();
        }
        else if (selectedEdge) {
          thisGraph.edges.splice(thisGraph.edges.indexOf(selectedEdge), 1);
          state.selectedEdge = null;
          thisGraph.updateGraph();
        }
        break;
    }
  };

  GraphCreator.prototype.svgKeyUp = function() {
    const thisGraph = this;
    thisGraph.destroyBrush();
    this.state.lastKeyDown = -1;
  };

  GraphCreator.prototype.removeNodeEdges = function(node){
    const thisGraph = this;
    thisGraph.edges.deleteNodeEdges(node);
  };

  GraphCreator.prototype.removeNode = function (node) {
    const thisGraph = this;
    // first remove edges
    thisGraph.removeNodeEdges(node);

    // remove children
    const children = node.getAllChildrenRef();

    for(let i=0; i < children.length; i++){
      thisGraph.removeNode(children[i]);  // removing children recursively
    }
    // remove itself
    node.reset();
    thisGraph.nodes.delete(node.id);
  };

  GraphCreator.prototype.removeNodeChildren = function(node, place){
    const thisGraph = this;
    let toRemove;
    if(place !== undefined){ // remove at specific position
      toRemove = node.getChildrenAtPosition(place);
    } else{ // remove all children
      toRemove = node.getAllChildrenRef();
    }
    for(let i=0; i < toRemove.length; i++){
      thisGraph.removeNode(toRemove[i]);
    }

    thisGraph.updateGraph();
  };

  // call to propagate changes to graph
  GraphCreator.prototype.updateGraph = function() {
    const thisGraph = this,
      consts = thisGraph.consts,
      state = thisGraph.state;

    // update existing nodes
    const nodes = Array.from(thisGraph.nodes.values());

    thisGraph.boats = d3.select("#all-boats-id").selectAll("g").data(nodes, function(d) {
      return d.id + d.label;
    });


    thisGraph.boats
      .attr("transform", function(d) {
      return "translate(" + d.x + "," + d.y + ")";
      })
      .classed("node-selected",function(d){
        return d.selected;
      })
      .on("mouseover", function(d) {
      const x = d.x,
        y = d.y,
        t = thisGraph.transformed;

      if(d.label.length >= 18){
        thisGraph.tooltip.transition()
          .duration(100)
          .style("opacity", .9);
        thisGraph.tooltip.html(d.label)
          .style("left", thisGraph.getPointTransformX(x) + t.k * 70 + "px")
          .style("top", thisGraph.getPointTransformY(y) - 15 + "px");
      }
      })
      .on("mouseout", function(d) {
        thisGraph.tooltip.transition()
          .duration(200)
          .style("opacity", 0);
      });


    // add new nodes
    const boats = thisGraph.boats.enter();
    const newGs = boats
      .append("g");

    newGs.classed(consts.boatGClass, true)
      .attr("transform", function(d) {
        thisGraph.range.x.max = Math.max(d.x, thisGraph.range.x.max);
        thisGraph.range.y.max = Math.max(d.y, thisGraph.range.y.max);

        thisGraph.range.x.min = Math.min(d.x, thisGraph.range.x.min);
        thisGraph.range.y.min = Math.min(d.y, thisGraph.range.y.min);

        return "translate(" + d.x + "," + d.y + ")";
      })
      .call(thisGraph.drag);

    newGs
      .filter(d => d.isNodeCumulator())
      .append("circle")
      .attr('r', 25)
      .attr("stroke-width", "1px")
      .attr("stroke", "black")
      .attr("fill", "#aaffaf")
      .classed("inner-node", true)
      .on('contextmenu', function(d){
        d3.event.preventDefault();
        thisGraph.lastClickedNodeCumulator = d;
        let hiddenNodes = d.getHiddenListHtml();
        thisGraph.hiddenList
          .style("left", thisGraph.transformed.x + (d.x +30)* thisGraph.transformed.k + "px")
          .style("top", thisGraph.transformed.y + (d.y + 15)  * thisGraph.transformed.k + "px")
          .style("display", "block")
          .html(hiddenNodes.html);
        d.addOnclickToCreatedNodes(thisGraph, hiddenNodes.createdIds, hiddenNodes.hiddenRef);
        });

    newGs
      .filter(d => !d.isNodeCumulator())
      .append("polyline")
      .classed("inner-node", true)
      .attr("points", "-50,10 -10,15 10,15 50,10 50,-10 10,-15 -10,-15 -50,-10 -50,10")
      .attr("stroke-width", "1px")
      .attr("stroke", "black")
      .attr("fill", function(d){
        return d.type.color || "red";
      })
      .on('click', function(d){
        let event = new CustomEvent("nodeClicked", { "detail": {node: d} });
        document.dispatchEvent(event);
      });


    // TODO append 4 action buttons
    newGs.each(function(d){
      thisGraph.appendChildrenButtons(d3.select(this), d);
    });

    newGs.each(function(d) {
      thisGraph.getNodeTitle(d3.select(this), d.label);
    });

    try{
      thisGraph.boats.enter().merge( d3.select("#all-boats-id").selectAll("text"));
    } catch (e){
      console.log(e);
    }
    // remove old nodes
    thisGraph.boats.exit().remove();


    let edges = Array.from(thisGraph.edges.values());
    const paths = d3.select("#all-paths-id").selectAll("path").data(edges, function(d){
      return d.id;
    })
      .classed(consts.selectedClass, function(d) {
        return d === state.selectedEdge;
      })
      .attr("d", function(d) {
        return "M" + d.source.x + "," + d.source.y + "L" + d.target.x + "," + d.target.y;
      });

    paths.exit().remove(); // EXIT

    paths.enter().append("path") // ENTER
      .merge(paths) // ENTER + UPDATE
      .style('marker-end', 'url(#end-arrow)')
      .style('marker-start', 'url(#start-arrow)')
      .style('stroke-width', '1.25')
      .classed("link", true)
      .classed("dotted", function(d){
        return d.dotted;
      })
      .attr("stroke", function (d) {
        return d.color;
      })
      .attr("d", function(d) {
        const connectionPoints = thisGraph.getConnectionPoints(d);
        if(d.target.isNodeCumulator()){
          return "M" + connectionPoints.source.x + "," + connectionPoints.source.y + "L" + d.target.x + "," + d.target.y;
        }
        return "M" + connectionPoints.source.x + "," + connectionPoints.source.y + "L" + connectionPoints.target.x + "," + connectionPoints.target.y;
      })
      .attr("id", function (d,i) { return "path_" + String(d.source.id)+ '-' + String(d.target.id); });


    const pathsText = d3.select("#all-paths-text-id").selectAll("text").data(edges, function(d, i){
      return String(d.source.id)+ '-' + String(d.target.id);
    });

    const text = pathsText.enter()
        .append("text")
        .classed("relation-text", true)
        .attr("dy", -5);

    text.exit().remove();

    text
        .append("textPath")
        .attr("startOffset", "5%")
        .attr("xlink:href", function (d) { return "#path_" + String(d.source.id)+ '-' + String(d.target.id); })
        .text(function (d) { return d.pathTextSrc  }); // + " "+ d.rel;});

    text
       .append("textPath")
       .attr("startOffset", "75%")
       .attr("xlink:href", function (d) { return "#path_" + String(d.source.id)+ '-' + String(d.target.id); })
       .text(function (d) { return d.pathTextTarget; });

    pathsText.exit().remove();
    if(thisGraph.minimap){
      thisGraph.minimap.updateMaxValues(thisGraph.range);
      thisGraph.minimap.render();
    }
    // minimapScale.minimapScale(minimapScale * 0.9);

  };

  GraphCreator.prototype.appendChildrenButtons = function(nodeHandle, d){
    const thisGraph = this;

    if(d.childrenTop.length > 0){
      const triangleTop = nodeHandle.append("polyline")
        .attr("points", '-13 -15, 13 -15, 0 -5, -13 -15')
        .attr("stroke-width", "1.5px")
        .attr("fill", "blue")
        .attr("stroke", "black")
        .classed("expanded", function(d){ return d.expandedTop})
        .on("click", function(){
          // this refering to triangle handle
          d.expandTriangleClick(thisGraph, this, 0);
        });
    }
    if(d.childrenRight.length > 0) {
      const shapeRight = nodeHandle.append("polyline")
        .attr("points", "50,10 40 0, 50 -10")
        .attr("stroke-width", "1.5px")
        .attr("fill", "blue")
        .attr("stroke", "black")
        .classed("expanded", function(d){ return d.expandedRight})
        .on("click", function () {
          d.expandTriangleClick(thisGraph, this, 1);
        });
    }
    if(d.childrenBottom.length > 0) {
      const triangleBottom = nodeHandle.append("polyline")
        .attr("points", '-13 15, 13 15, 0 5, -13 15')
        .attr("stroke-width", "1.5px")
        .attr("fill", "blue")
        .attr("stroke", "black")
        .classed("expanded", function(d){ return d.expandedBottom})
        .on("click", function () {
          d.expandTriangleClick(thisGraph, this, 2);
        });
    }
    if(d.childrenLeft.length > 0) {
      const shapeLeft = nodeHandle.append("polyline")
        .attr("points", "-50,10 -40 0, -50 -10")
        .attr("stroke-width", "1.5px")
        .attr("fill", "blue")
        .attr("stroke", "black")
        .classed("expanded", function(d){ return d.expandedLeft})
        .on("click", function () {
          d.expandTriangleClick(thisGraph, this, 3);
        });
    }
  };

  GraphCreator.prototype.getConnectionPoints = function(edge) {
    /*
     * array of len 2,
     * [0] -> connection point source
     * [1] -> connection point destination
     * 4 possible connection points
     * 0, 1, 2, 3 -> top, right, bottom, left
     */
    const thisGraph = this;
    try {
      return {
        source: thisGraph.getConnectionPoint(edge.connectionPoints[0], edge.source.x, edge.source.y),
        target: thisGraph.getConnectionPoint(edge.connectionPoints[1], edge.target.x, edge.target.y),
      }
    }
    catch (err){
      console.log(edge);
    }
  };

  GraphCreator.prototype.getConnectionPoint = function(mode, x, y) {
    const thisGraph = this;
    const ret = {
      x: x,
      y: y
    };
    switch (mode) {
      case 0:
        ret['y'] -= thisGraph.consts.nodeHeight / 2 - 1;
        break;
      case 1:
        ret['x'] += 51;
        break;
      case 2:
        ret['y'] += thisGraph.consts.nodeHeight / 2 - 1;
        break;
      case 3:
        ret['x'] -= 51;
        break;
      default:
    }
    return ret;
  };

  GraphCreator.prototype.zoomed = function(transform) {
    const thisGraph = this;

    transform = transform || d3.event.transform;

    thisGraph.state.justScaleTransGraph = true;
    d3.select("." + this.consts.graphClass)
      .attr("transform", transform);
    thisGraph.transformed = transform;
    thisGraph.hiddenList
      .style("display", "none");

    if(thisGraph.minimap)
      thisGraph.minimap.update(transform);
  };

  GraphCreator.prototype.updateWindow = function(svg) {
    const docEl = document.documentElement,
      bodyEl = document.getElementsByTagName('body')[0],
      x = window.innerWidth || docEl.clientWidth || bodyEl.clientWidth,
      y = window.innerHeight || docEl.clientHeight || bodyEl.clientHeight;
    svg.attr("width", x).attr("height", y);
  };

  GraphCreator.prototype.initializeFromSynsetId = function(id){
    const thisGraph = this;
    const callback = function(d){
      if(d.length < 1){
        window.alert('Sorry, could not display requested data.');
        return;
      }
      thisGraph.initFromJson(d);
    };

    thisGraph.api.getGraph(id, function(d){
      callback(d);
    });
  };

  /* exporting picture
   * reference Nikita Rokotyan - http://bl.ocks.org/Rokotyan/0556f8facbaf344507cdc45dc3622177
   * using FileSaver.js library & Canvas-to-Blob.js
   */
  GraphCreator.prototype.exportVisualization = function(){
    const thisGraph = this;
    const svgString = thisGraph.getSVGString(thisGraph.svg.node());
    thisGraph.svgString2Image( svgString, 2*thisGraph.width, 2*thisGraph.height, 'png', save ); // passes Blob and filesize String to the callback

    function save( dataBlob, filesize ){
      saveAs( dataBlob, 'wordnet-graph.png' ); // FileSaver.js function
    }
  };

  // Below are the functions that handle actual exporting:
  // getSVGString ( svgNode ) and svgString2Image( svgString, width, height, format, callback )
  GraphCreator.prototype.getSVGString = function(svgNode) {
    svgNode.setAttribute('xlink', 'http://www.w3.org/1999/xlink');
    const cssStyleText = getCSSStyles( svgNode );
    appendCSS( cssStyleText, svgNode );

    const serializer = new XMLSerializer();
    let svgString = serializer.serializeToString(svgNode);
    svgString = svgString.replace(/(\w+)?:?xlink=/g, 'xmlns:xlink='); // Fix root xlink without namespace
    svgString = svgString.replace(/NS\d+:href/g, 'xlink:href'); // Safari NS namespace fix

    return svgString;

    function getCSSStyles( parentElement ) {
      let selectorTextArr = [];

      // Add Parent element Id and Classes to the list
      selectorTextArr.push( '#'+parentElement.id );
      for (let c = 0; c < parentElement.classList.length; c++)
        if ( !contains('.'+parentElement.classList[c], selectorTextArr) )
          selectorTextArr.push( '.'+parentElement.classList[c] );

      // Add Children element Ids and Classes to the list
      const nodes = parentElement.getElementsByTagName("*");
      for (let i = 0; i < nodes.length; i++) {
        let id = nodes[i].id;
        if ( !contains('#'+id, selectorTextArr) )
          selectorTextArr.push( '#'+id );

        const classes = nodes[i].classList;
        for (let c = 0; c < classes.length; c++)
          if ( !contains('.'+classes[c], selectorTextArr) )
            selectorTextArr.push( '.'+classes[c] );
      }

      // Extract CSS Rules
      let extractedCSSText = "";
      for (let i = 0; i < document.styleSheets.length; i++) {
        let s = document.styleSheets[i];

        try {
          if(!s.cssRules) continue;
        } catch( e ) {
          if(e.name !== 'SecurityError') throw e; // for Firefox
          continue;
        }

        let cssRules = s.cssRules;
        for (let r = 0; r < cssRules.length; r++) {
          if ( contains( cssRules[r].selectorText, selectorTextArr ) )
            extractedCSSText += cssRules[r].cssText;
        }
      }
      return extractedCSSText;

      function contains(str,arr) {
        return !(arr.indexOf( str ) === -1);
      }
    }

    function appendCSS( cssText, element ) {
      const styleElement = document.createElement("style");
      styleElement.setAttribute("type","text/css");
      styleElement.innerHTML = cssText;
      const refNode = element.hasChildNodes() ? element.children[0] : null;
      element.insertBefore( styleElement, refNode );
    }
  };

  GraphCreator.prototype.svgString2Image = function( svgString, width, height, format, callback ) {
    format = format ? format : 'png';

    const imgsrc = 'data:image/svg+xml;base64,'+ btoa( unescape( encodeURIComponent( svgString ) ) ); // Convert SVG string to data URL

    const canvas = document.createElement("canvas");
    const context = canvas.getContext("2d");

    canvas.width = width;
    canvas.height = height;

    const image = new Image();
    image.onload = function() {
      context.clearRect ( 0, 0, width, height );
      context.drawImage(image, 0, 0, width, height);

      canvas.toBlob( function(blob) {
        const filesize = Math.round( blob.length/1024 ) + ' KB';
        if ( callback ) callback( blob, filesize );
      });
    };
    image.src = imgsrc;
  };

  GraphCreator.prototype.resetZoom = function(){
    const thisGraph = this;

    let transform = d3.zoomIdentity;

    thisGraph.zoomed(transform);
    thisGraph.svg.property("__zoom", transform);
    if(thisGraph.minimap){
      thisGraph.minimap.resetScale();
    }
  };

  GraphCreator.prototype.resizeSVG = function (width, height) {
    const thisGraph = this;
    // setting values causing error in minimap
    // thisGraph.width = width;
    // thisGraph.height = height;
    thisGraph.svg.attr("width", width).attr("height", height);
  };



