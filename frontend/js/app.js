'use strict';

angular.module('esn.bpmn', [
  'op.dynamicDirective',
  'ui.router',
  'formly',
  'formlyBootstrap'
])

.config([
  '$stateProvider',
  'dynamicDirectiveServiceProvider',
  function($stateProvider, dynamicDirectiveServiceProvider) {
    var bpmnJs = new dynamicDirectiveServiceProvider.DynamicDirective(true, 'application-menu-cnet', {priority: 28});

    dynamicDirectiveServiceProvider.addInjection('esn-application-menu', bpmnJs);

    $stateProvider
      .state('bpmnEditor', {
        url: '/bpmn',
        templateUrl: '/bpmnJs/views/editor',
        controller: 'bpmnController'
      })

      .state('bpmnExecutor', {
        url: '/bpmn/executor',
        templateUrl: '/bpmnJs/views/executor',
        controller: 'bpmnExecutor'
      })

      .state('bpmnTask', {
        url: '/bpmn/task',
        templateUrl: '/bpmnJs/views/task',
        controller: 'bpmnTask'
      })
      ;
  }
]);
