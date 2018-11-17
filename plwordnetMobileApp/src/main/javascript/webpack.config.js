var path= require('path');

module.exports = {
  entry: [
    "./graph/js/canvas-toBlob.js",
    "./graph/js/FileSaver.min.js",
    "./graph/js/api-connector.js",
    "./graph/js/graph-edges-container.js",
    "./graph/js/graph-node.js",
    "./graph/js/graph-minimap.js",
    "./graph/js/graph-creator.js"
  ],
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'graph-creator.js',
    library: 'GraphCreator'
  }
};