'use strict';

angular.module('esn.bpmn')
  .factory('userService', function($http, tokenAPI, fileUploadService) {

    var listFileUrl = '/bpmnJs/api/myfiles';
    var apiFileUrl = '/api/files/';
    var apiUserUrl = '/api/user/';

    var listFile = function() {
      return $http.get(listFileUrl).then(function(response) {
        return response;
      });
    };

    var getFileName = function(id) {
      return $http.get(apiFileUrl + id).then(function(response) {
        var dataJson = {};
        if(response.headers()['content-disposition'] !== undefined){
          dataJson.id = id;
          dataJson.name = response.headers()['content-disposition'].split('\"')[1];
        }
        return dataJson;
      });
    };

    var selectFile = function(id) {
      return $http.get(apiFileUrl + id).then(function(response) {
        return response;
      });
    };

    var deleteFile = function(id) {
      return $http.delete(apiFileUrl + id).then(function(response) {
        return response;
      });
    };

    var userInfo = function() {
      return $http.get(apiUserUrl).then(function(response) {
        return response.data;
      });
    };

    var writeFile = function(file) {
      fileUploadService.get().addFile(file, true);
    };

    var getToken = function() {
      return tokenAPI.getNewToken().then(function(response) {
        return response.data;
      });
    };

    return {
      deleteFile:deleteFile,
      getFileName:getFileName,
      listFile:listFile,
      selectFile:selectFile,
      userInfo:userInfo,
      writeFile:writeFile,
      getToken:getToken
    };
  });
