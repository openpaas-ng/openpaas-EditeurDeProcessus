'use strict';

angular.module('esn.bpmn')
.directive('applicationMenuCnet', function(applicationMenuTemplateBuilder) {
  return {
    retrict: 'E',
    replace: true,
    template: applicationMenuTemplateBuilder('/#/bpmn', 'c2net', 'Processus')
  };
})

.directive('subheaderMenu', function() {
  return {
    restrict: 'E',
    templateUrl: '/bpmnJs/views/subHeader.html'
  };
})
;
