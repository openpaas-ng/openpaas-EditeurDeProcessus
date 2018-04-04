'use strict';

var express = require('express');

// This is you own express application
module.exports = function(dependencies) {

  var application = express();

  // Every express new configuration are appended here.
  // This needs to be initialized before the body parser
  require('./config/i18n')(dependencies, application);
  require('./config/views')(dependencies, application);

  return application;
};
