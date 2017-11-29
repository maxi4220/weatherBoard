'use strict';

/**
 * @ngdoc function
 * @name weatherBoardApp.controller:AppCtrl
 * @description
 * # AppCtrl
 * Controller of the weatherBoardApp
 */
weatherBoardApp
  .controller('MainCtrl', ["$rootScope", "$scope", "ngNotify", "mainService", ($rootScope, $scope, ngNotify, mainService) => {

    $rootScope.welcomeShowed = true;
    $rootScope.boardsShowed = false;
    $scope.boards = [];
    $scope.user = {};
    $scope.selectedIdBoard = 0;
    $scope.selectedCities = [];
    $scope.cities = [];
    $scope.allCities = [];
    $scope.socket = null;


    $scope.login = () => {
        ngNotify.set("Logging in ...", "info");
        try{
            const userName = angular.element("#userName").val();
            if(userName===""){
                ngNotify.set("Please provide your user name.", "warn");
            }else{

                // Calls the login service which consumes the REST API login method
                mainService.login(userName, (response) => {
                    
                    // Got an error
                    if(response.status !== "success") {
                        ngNotify.set(response.data, response.status);
                    } else {

                        $scope.defineSocketEvents();

                    	$scope.cities = [];
                        const user = {"id":response.data, "name":userName};
                        // Validated the user is found
                        angular.element(document.querySelector('#loginForm')).modal('hide');
                        $rootScope.welcomeShowed = false;
                        
                        $scope.user = {
                        	"id": response.data,
                        	"name": userName,
                        	"isLoggedIn": true
                        };

                        ngNotify.set(`User ${userName} logged in!`, response.status);

                        $scope.loadCities(() => {
                        	$scope.showBoards(user.name);
                        });                        
                    }
                });
            }
        } catch(ex) {
            ngNotify.set(ex.message, "error");
        }
    };

    $scope.loginEvent = e => {
        // If enter key was pressed
        if(e && e.which === 13){
            // Call login function
            $scope.login();
        }
    };

    $scope.selectCity = idCity => {
    	const idx = $scope.selectedCities.indexOf(idCity);
    	if(idx>-1) {
    		$scope.selectedCities.splice(idx, 1);
    	} else {
    		$scope.selectedCities.push(idCity);
    	}
    };    

    $scope.loadCities = (callback) => {
		// Gets all cities 
		mainService.loadCities(response => {
            // Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
                $scope.cities = response.data;
                $scope.allCities = response.data;
                callback();
			}
		});
    };

    $scope.addCities = () => {
		// Add a new board for the current user
		mainService.addCities($scope.selectedIdBoard, $scope.selectedCities, response => {
            // Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
                ngNotify.set("Cities added!", response.status);

                // Adds the selected cities to the stored array 
        		const i = mainService.getBoardByIdBoard($scope.selectedIdBoard, $scope.boards);
        		$scope.boards[i].cities = $scope.boards[i].cities.concat(response.data);
        		$scope.emitCitiesStatus();
			}
		});
    };

    $scope.removeCity = (idBoard, idCity) => {
		// Removes the selected sity from the current board
		mainService.removeCity(idBoard, idCity, response => {
            // Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
                ngNotify.set("City removed!", response.status);

        		const i = mainService.getBoardByIdBoard(idBoard, $scope.boards);
        		for(let j = 0; j < $scope.boards[i].cities.length; j++) {
        			if($scope.boards[i].cities[j].id === idCity) {
        				$scope.boards[i].cities.splice(j, 1);
        				break;
        			}
        		}
        		$scope.emitCitiesStatus();
			}
		});
    };

    $scope.getCities = (idBoard, idBoards) => {

        // Gets status of all cities in a board
        mainService.getCitiesByIdBoard(idBoard, response => {
			
			// Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
            	// Creates the board object and pushes it to our boards array
        		const board = {"id": idBoard, "cities": response.data};
        		$scope.addBoardCities(board);
        		
        		idBoards.splice(idBoards.indexOf(idBoard),1);
        		
        		if(idBoards.length>0){
        			$scope.getCities(idBoards[0], idBoards);
        		} else {
        			$rootScope.boardsShowed = true;
        		}
            }
        });

    };

    $scope.selectBoard = idBoard => {
    	
    	$scope.selectedIdBoard = idBoard;
    	$scope.selectedCities = [];
    	const cities = angular.element(".chkCities");
    	for(let city of cities) {
    		city.checked = false;
    	}
    };

    $scope.showBoards = userName => {
        // Calls the getBoards function of the boards controller
        mainService.getBoards(userName, response => {
			// Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {

				const idBoards = response.data;
				$scope.boards = [];
				$scope.getCities(idBoards[0], idBoards);
            	
            }
        });
    };

    $scope.addBoard = () => {
		// Add a new board for the current user
		mainService.addBoard($scope.user.name, response => {
            // Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
                ngNotify.set("Board added!", response.status);

                $scope.showBoards($scope.user.name);
			}
		});
    };

    $scope.addBoardCities = boardCities => {
    	$scope.boards.push(boardCities);
    };

    $scope.emitCitiesStatus = () => {
        if($scope.socket){
        	var citiesStatus = [];
        	for(let board of $scope.boards){ 
        		for(let city of board.cities) {
        			if(citiesStatus.indexOf(city.id)===-1){
        				citiesStatus.push(city.id);
        			}
        		}
        	}
        	//$scope.socket.emit("citiesStatus", JSON.stringify(citiesStatus));
        }
    };

    $scope.defineSocketEvents = () => {
        
        if(!$scope.socket){
            $scope.socket = io.connect('http://localhost:9000');
            $scope.socket.on("citiesStatus", savedCities => {
                $scope.$apply(() => {
                    let boards = $scope.boards;
                    let cities;

                    savedCities = JSON.parse(savedCities);
                    
                    // Loops all updated cities
                    for(let i = 0, savedCitiesLen = savedCities.length;
                            i < savedCitiesLen;
                            i++) {

                        // Loops the current user boards
                        for(let j = 0, boardsLen = boards.length;
                                j < boardsLen;
                                j++) {

                            // Current board
                            cities = boards[j].cities;

                            // Loops all cities of the current board
                            for(let k = 0, citiesLen = cities.length;
                                k < citiesLen;
                                k++) {

                                // Updates our $scope cities array so that the changes are seen on the view
                                if($scope.boards[j].cities[k].id == savedCities[i].id) {
                                    $scope.boards[j].cities[k].humidity = savedCities[i].humidity;
                                    $scope.boards[j].cities[k].pressure = savedCities[i].pressure;
                                    $scope.boards[j].cities[k].temp = savedCities[i].temp;
                                    $scope.boards[j].cities[k].text = savedCities[i].text;
                                }
                            }
                        }
                    }
                });                
            });
        } 
    };
}]);