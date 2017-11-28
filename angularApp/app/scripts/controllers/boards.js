'use strict';

/**
 * @ngdoc function
 * @name weatherBoardApp.controller:BoardsCtrl
 * @description
 * # BoardsCtrl
 * Controller of the weatherBoardApp
 */
weatherBoardApp
  .controller('BoardsCtrl', ($scope, boardsService) => {
    $scope.boards = [];
    $scope.showBoards = () => {
        $scope.boards = boardsService.showBoards();
        /*
        $http.get('http://localhost:8080/cities?idBoard=1')
        .then(response => {
        	if(response.data.length===0){
        		$scope.cities = JSON.parse("{\"error\":\"No cities found\"}")
        	} else {
            	$scope.cities = response.data
        	}
        })
        */
    }
  })
