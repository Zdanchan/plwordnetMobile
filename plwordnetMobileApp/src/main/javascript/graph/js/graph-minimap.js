/** MINIMAP **/
// original source => https://codepen.io/billdwhite/pen/qPWKOg
d3.minimap = function() {

  "use strict";

  var minimapScale    = 0.15,
    originalScale   = .15,
    host            = null,
    base            = null,
    target          = null,
    width           = 100,
    height          = 100,
    originalWidth   = 100,
    originalHeight  = 100,
    x               = 0,
    y               = 0,
    targetShadow    = [];

  function minimap(selection) {

    base = selection;

    var zoom = d3.zoom()
      .scaleExtent([0.5, 5]);

    // updates the zoom boundaries based on the current size and scale
    var updateMinimapZoomExtents = function() {
      var scale = container.property("__zoom").k;
      var targetWidth = parseInt(target.attr("width"));
      var targetHeight = parseInt(target.attr("height"));
      var viewportWidth = host.getWidth();
      var viewportHeight = host.getHeight();
      zoom.translateExtent([
        [-viewportWidth/scale, -viewportHeight/scale],
        [(viewportWidth/scale + targetWidth), (viewportHeight/scale + targetHeight)]
      ]);
    };

    var zoomHandler = function() {
      frame.attr("transform", d3.event.transform);
      // here we filter out the emitting of events that originated outside of the normal ZoomBehavior; this prevents an infinite loop
      // between the host and the minimap
      if (d3.event.sourceEvent instanceof MouseEvent || d3.event.sourceEvent instanceof WheelEvent) {
        // invert the outgoing transform and apply it to the host
        var transform = d3.event.transform;
        // ordering matters here! you have to scale() before you translate()
        var modifiedTransform = d3.zoomIdentity.scale(1/transform.k).translate(-transform.x, -transform.y);
        host.update(modifiedTransform);
      }

      updateMinimapZoomExtents();
    };

    zoom.on("zoom", zoomHandler);

    var container = selection.append("g")
      .attr("class", "minimap clarin-graph-visualization");

    container.call(zoom);

    minimap.node = container.node();

    var frame = container.append("g")
      .attr("class", "frame");

    frame.append("rect")
      .attr("class", "background")
      .attr("width", width)
      .attr("height", height);

    targetShadow.forEach( id => {
      container.append('use')
        .attr('xlink:href', id);
    });

    minimap.update = function(hostTransform) {
      // invert the incoming zoomTransform; ordering matters here! you have to scale() before you translate()
      var modifiedTransform = d3.zoomIdentity.scale((1/hostTransform.k)).translate(-hostTransform.x, -hostTransform.y);
      // call this.zoom.transform which will reuse the handleZoom method below
      zoom.transform(frame, modifiedTransform);
      // update the new transform onto the minimapCanvas which is where the zoomBehavior stores it since it was the call target during initialization
      container.property("__zoom", modifiedTransform);

      updateMinimapZoomExtents();
    };


    let minimapRange = {
      x: null,
      y: null,
      xRatio: null,
      yRatio: null,
      widthRatio: null,
      heightRatio: null,
    };

    minimap.resetScale = function(){
      minimapRange = {
        x: null,
        y: null,
        xRatio: null,
        yRatio: null,
        widthRatio: null,
        heightRatio: null,
      };

      minimapScale = originalScale;
      x = 0;
      y = 0;
      width = originalWidth;
      height = originalHeight;
    };

    /** RENDER **/
    minimap.render = function() {
      // update the placement of the minimap

      container
        .attr("transform", "translate(" + x + "," + y + ")scale(" + minimapScale + ")");

      // keep the minimap's viewport (frame) sized to match the current visualization viewport dimensions
      frame.select(".background")
        .attr("width", width)
        .attr("height", height);
      frame.node().parentNode.appendChild(frame.node());

    };

    minimap.updateMaxValues = function(range){
      let xRange = range.x.max - range.x.min;
      let yRange = range.y.max - range.y.min;

      if(!minimapRange.x || !minimapRange.y){
        minimapRange.x = xRange;
        minimapRange.y = yRange;

        minimapRange.xRatio = xRange * minimapScale;
        minimapRange.yRatio = yRange * minimapScale;
        return
      }

      let newScale = Math.min(minimapRange.xRatio / xRange, minimapRange.yRatio / yRange);
      if(newScale < minimapScale){
        x = (width * originalScale - width * newScale) / 2;
        y = (height * originalScale - height * newScale) / 2;

        minimapScale = newScale;
      }

    };

    updateMinimapZoomExtents();
  }


  //============================================================
  // Accessors
  //============================================================

  minimap.targetShadowId = function(id) {
    targetShadow.push(id);
    return this;
  };

  minimap.width = function(value) {
    if (!arguments.length) return width;
    width = parseInt(value, 10);
    return this;
  };


  minimap.height = function(value) {
    if (!arguments.length) return height;
    height = parseInt(value, 10);
    return this;
  };


  minimap.x = function(value) {
    if (!arguments.length) return x;
    x = parseInt(value, 10);
    return this;
  };


  minimap.y = function(value) {
    if (!arguments.length) return y;
    y = parseInt(value, 10);
    return this;
  };


  minimap.host = function(value) {
    if (!arguments.length) { return host;}
    host = value;
    return this;
  };


  minimap.minimapScale = function(value) {
    if (!arguments.length) { return minimapScale; }
    originalScale = minimapScale = value;
    return this;
  };


  minimap.target = function(value) {
    if (!arguments.length) { return target; }
    target = value;
    originalWidth = width  = parseInt(target.attr("width"),  10);
    originalHeight = height = parseInt(target.attr("height"), 10);

    return this;
  };

  return minimap;
};
