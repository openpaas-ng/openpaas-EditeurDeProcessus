'use strict';

function getMessage() {
  return 'Hello World!';
}

module.exports = function(dependencies) {
  return {
    getMessage: getMessage
  };
};
