'use strict'

// webpack build configuration
// see https://webpack.js.org/configuration/
// for a more detailed explanation of the
// options specified here

const webpack = require('webpack')
const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const DashboardPlugin = require('webpack-dashboard/plugin')

// lets us see & debug pre-compiled code in the browsery
// different values have different levels of quality and
// performance impact
const devtool =
  'source-map'

// what are the primary "main methods" of our app?
const entry = {
  // we only have one right now, main.js, which loads
  // our app.module, bootstrapping our app
  main: [
    // ui foundations
    'jquery',

    // ui framework
    'angular',

    // useful css and jquery plugins
    'bootstrap',

    // adds unsupported language features, like
    // Promises and Proxies
    'babel-polyfill',

    // the path to our entry script
    path.resolve(
      // the directory this (as in, the one you're reading)
      // file lives in
      __dirname,
      // the path to our entry script, relative to this file's
      // directory
      'src/main.js'
    )
  ]
}

// where webpack puts its compilation results
const output = {
  path: path.resolve(__dirname, 'dist'),
  filename: '[name].[hash].js'
}

// rules for how webpack finds imported files
const resolve = {
  // allows loading of .js, .css, and .html files
  // with or without extensions
  extensions: [
    '.js',
    '.less',
    '.html',
    '.css'
  ],
  // allows absolute module imports (i.e. import 'foo'
  // instead of import './foo') from node_modules
  // (allowed by default) and lib folders (not allowed
  // by default), following node's directory-based
  // module scoping rules
  modules: [
    'node_modules',
    'src',
    'lib'
  ]
}

// rules tell webpack what loaders to use on which files
// each rule defines which files it matches, which loaders
// to use, which options to configure those loaders with,
// and in what order they should be applied
const rules = [{
  // javascript rule
  // uses babel to process ES6 JS and emit
  // browser-compatible JS

  // the test is usually a regex, and webpack uses it on
  // every file loaded to see if the rule it's associated with
  // should be applied
  test: /\.js$/,

  // exclude tells webpack to ignore certain file paths for this loader
  // entirely. Here, the regex /node_modules/ says to exclude any file
  // path that includes the substring "node_modules"
  exclude: [/node_modules/],

  // use tells webpack which loaders to use for this rule.
  use: [
    // babel loader will read the babel configuration in
    // our package.json
    'babel-loader'
  ]
}, {
  // css loading
  test: /\.(css|less)$/,
  use: [
    // style-loader takes the css string
    // exported by css-loader and injects it
    // into a <style></style> tag in the
    // document header
    'style-loader',
    // css-loader loads a css file as a string
    // and exports that string along with
    // css class names mentioned in the file
    'css-loader',
    // better css
    'less-loader'
  ]
}, {
  // raw html loading
  // used by HtmlWebpackPlugin
  test: /\.html$/,
  // we want to use ngtemplate-loader for angular templates,
  // so this html rule only applies to html files from outside of
  // the project's src folder
  exclude: [path.resolve(__dirname, 'src')],
  use: 'html-loader'
}, {
  // angular template loading
  // used to load angular templates (html files) into
  // the angular template cache automatically,
  // greatly improving render speed
  test: /\.html$/,
  // we don't want this rule to apply to any html files other than angular
  // templates, so we exclude the static directory and all node_modules
  // directories
  exclude: [/node_modules/, path.resolve(__dirname, 'static')],
  use: [
    // ngtemplate-loader takes the html string exported by
    // html-loader, adds it to angular's template cache, and exports
    // a string that represents the template's url
    'ngtemplate-loader',
    'html-loader'
  ]
}, {
  test: /\.(eot|svg|ttf|woff|woff2)$/,
  use: [
    'url-loader?limit=1000'
  ]
}]

// plugins are additions to webpack that aren't tied
// to the loading of individual modules, but instead
// apply to the entire webpack build process
const plugins = [
  // the HtmlWebpackPlugin is used to inject our compiled
  // javascript files into a given template
  // index.html file, allowing us to use auto-generated
  // file names
  new HtmlWebpackPlugin({
    title: 'ftd-buttons',
    // tells webpack to inject the compiled files into the
    // head of the document
    inject: 'head',
    // path to the index.html template file
    template: path.resolve(__dirname, 'static/index.html')
  }),
  // the DashboardPlugin makes the webpack dev server CLI look
  // waaaay prettier ;)
  // see the package.json's script section to see how it's used
  new DashboardPlugin(),
  // provides jquery and angular under the given names to all
  // modules
  new webpack.ProvidePlugin({
    $: 'jquery',
    ng: 'angular',
    jQuery: 'jquery'
  })
]

// configures the webpack dev server, which serves the compiled bundle
// and watches source files for changes, recompiling and re-serving the
// app automatically. Hot reloading (automatic client update) can be enabled
// here, but it is easier to enable as a commandline flag. See the
// package.json's script section to see how.
const devServer = {
  // use gzip compression to send files to the browser.
  // improves network performance
  compress: true,
  // render the app inline. by default, the app will be served
  // inside of an iframe
  inline: true,
  // render a fullscreen overlay on the client when the dev server
  // encounters an error.
  overlay: true,
  // allows for SPA routing without a path  prefix
  historyApiFallback: true,
  port: 8181
}

// the entire configuration must be exported for webpack to
// recognize it
module.exports = {
  devtool,
  entry,
  output,
  resolve,
  module: { rules },
  plugins,
  devServer
}
