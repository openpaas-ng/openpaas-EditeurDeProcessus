'use strict';

var AwesomeModule = require('awesome-module');

var Dependency = AwesomeModule.AwesomeModuleDependency;
var path = require('path');

var myAwesomeModule = new AwesomeModule('esn.bpmn', {
  dependencies: [
    new Dependency(Dependency.TYPE_NAME, 'linagora.esn.core.logger', 'logger'),
    new Dependency(Dependency.TYPE_NAME, 'linagora.esn.core.auth', 'auth'),
    new Dependency(Dependency.TYPE_NAME, 'linagora.esn.core.webserver.wrapper', 'webserver-wrapper'),
    new Dependency(Dependency.TYPE_NAME, 'linagora.esn.core.filestore', 'filestore'),
    new Dependency(Dependency.TYPE_NAME, 'linagora.esn.core.webserver.middleware.authorization', 'authorizationMW')
  ],

  states: {
    lib: function(dependencies, callback) {
      var bpmnlib = require('./backend/lib')(dependencies);
      var bpmn = require('./backend/webserver/api/bpmn')(dependencies);

      var lib = {
        api: {
          bpmn: bpmn
        },
        lib: bpmnlib
      };

      return callback(null, lib);
    },

    deploy: function(dependencies, callback) {
      // Register the webapp
      var app = require('./backend/webserver')(dependencies, this);
      // Register every exposed endpoints
      app.use('/', this.api.bpmn);

      var webserverWrapper = dependencies('webserver-wrapper');
      // Register every exposed frontend scripts
      var angularFiles = [
        '../components/api-check/dist/api-check.js',
        '../components/angular-formly/dist/formly.js',
        '../components/angular-formly-templates-bootstrap/dist/angular-formly-templates-bootstrap.js',
        '../components/file-saver/FileSaver.min.js',
        'app.js',
        'services/bpmnServices.js',
        'services/userService.js',
        'core/directives.js',
        'core/controllers.js',
        'core/task.js',
        'core/executor.js',
        'task/listTask.js',
        'modules/bpmn-loader.js'
      ];

      webserverWrapper.injectAngularModules('bpmnJs', angularFiles, ['esn.bpmn'], ['esn']);
      var lessFile = path.resolve(__dirname, './frontend/css/styles.less');
      webserverWrapper.injectLess('bpmnJs', [lessFile], 'esn');
      webserverWrapper.addApp('bpmnJs', app);

      return callback();
    }
  }
});

/**
 * The main AwesomeModule describing the application.
 * @type {AwesomeModule}
 */
module.exports = myAwesomeModule;
