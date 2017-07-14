/* Babel 6's ES2015 preset renames all anonymous functions,
   transforming

   var f = function() {}

   into

   var f = function f() {}

   This can occasionally clash with annotated functions, because things like this:

   var f = ["a", function(a){}];

   will not undergo that transformation.  This causes
*/

'use strict';

const es2015 = require('babel-preset-es2015');
const bad = [
    require('babel-plugin-transform-es2015-function-name')
];

let plugins = es2015.plugins.filter(plugin => bad.indexOf(plugin) === -1);

module.exports = {
  plugins: plugins
}
