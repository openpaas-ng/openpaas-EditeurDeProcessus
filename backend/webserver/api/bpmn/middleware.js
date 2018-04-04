'use strict';

var logger;

function passThrough(req, res, next) {
  logger.info('It works! Passing through');
  next();
}

module.exports = function(dependencies) {
  logger = dependencies('logger');
  return {
    passThrough: passThrough
  };
};
