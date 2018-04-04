'use strict';

angular.module('esn.bpmn')

.factory('bpmnLoader', function($http) {
  var bpmnModeler = function() {
    return require('bpmn-js/lib/Modeler');
  };

  var bpmnPropertiesPanel = function() {
    return require('bpmn-js-properties-panel');
  };

  var bpmnPropertiesPanelProvider = function() {
    return require('bpmn-js-properties-panel/lib/provider/camunda');
  };

  var camundaModdleDescriptor = function() {
    return require('camunda-bpmn-moddle/resources/camunda');
  };

  return {
    bpmnModeler:bpmnModeler,
    bpmnPropertiesPanel:bpmnPropertiesPanel,
    bpmnPropertiesPanelProvider:bpmnPropertiesPanelProvider,
    camundaModdleDescriptor:camundaModdleDescriptor
  };
});
