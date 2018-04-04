'use strict';

angular.module('esn.bpmn')
  .controller('bpmnTask', function($scope, $window, $http, bpmnLoader, bpmnService, userService, $modal, notificationFactory, $state) {
    $scope.selectedTab = 'task';

    $scope.userToken = userService.getToken();

    $scope.isShow = false;
    $scope.hasTask = false;

    $scope.activitiName = 'Task form';
    $scope.activitiFields = {};

    $scope.goToEditor = function() {
      $state.go('bpmnEditor');
    };

    function userInfo() {
      return userService.userInfo().then(function(result) {
        $scope.userInfo = result;
        return result;
      }, function(err) {
        notificationFactory.weakError('Error', err);
      });
    }

    $scope.userInfo = userInfo();

    function refreshFormDataList() {
      return bpmnService.listActiveTaskForm($scope.userInfo).then(function(result) {
        if (result.data.length === 0) {
          $scope.hasTask = false;
        }else {
          $scope.hasTask = true;
        }
        $scope.bpmnFormDataList = result.data;
        return result.data;
      }, function(err) {
        notificationFactory.weakError('Error', err);
      });
    }

    $scope.bpmnFormDataList = refreshFormDataList();

    var myListTaskModal = $modal({title: 'Activiti list task', scope: $scope, template: 'bpmnJs/views/modal/listTask.html', show: false});
    $scope.showListTaskModal = function(id) {
      myListTaskModal.show();
    };

    $scope.closeModal = function() {
      myListTaskModal.hide();
    };

    $scope.selectTaskInformation = function(data) {
      return data[data.length - 1].templateOptions.placeholder;
    };

    $scope.selectForm = function(data) {
      $scope.activiti = {};
      $scope.activitiFields = data.form;
      $scope.isShow = true;
    };

    $scope.cancel = function() {
      $scope.isShow = false;
    };

    $scope.onSubmit = function(id) {
      if ($scope.activiti !== undefined) {

        bpmnService.completeTask($scope.activiti).then(function(result) {
          notificationFactory.weakSuccess('success', 'Task complete with id ' + $scope.activiti.taskId);
          $scope.isShow = false;
          refreshFormDataList();
          return result;
        }, function(err) {
          notificationFactory.weakError('danger', 'Error during the task execution : ' + err.data.message);
        });
      }
    };
  }
);
