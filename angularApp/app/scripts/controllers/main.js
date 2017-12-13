'use strict';

/**
 * @ngdoc function
 * @name weatherBoardApp.controller:AppCtrl
 * @description
 * # AppCtrl
 * Controller of the weatherBoardApp
 */
weatherBoardApp
  .controller('MainCtrl', ["$rootScope", "$scope", "ngNotify", "mainService", "$timeout", ($rootScope, $scope, ngNotify, mainService, $timeout) => {

    const ENTER_KEY = 13;
    const ESCAPE_KEY = 27;

    $rootScope.welcomeShowed = true;
    $rootScope.boardsShowed = false;
    $scope.boards = [];
    $scope.user = {};
    $scope.selectedIdBoard = 0;
    $scope.selectedCities = [];
    $scope.cities = [];
    $scope.socket = null;
    $scope.showAll = true;
    $scope.showEditBoardButton = [];
    $scope.tmpBoardName = "";
    $scope.loggedIn = false;
    $scope.extendedForecastCities = [];
    $scope.forecastDays = [];

    const DAY_NAMES = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    var movementStrength = 25;
    var height = movementStrength / $(window).height();
    var width = movementStrength / $(window).width();
    $(".background-image").mousemove(function(e){
              var pageX = e.pageX - ($(window).width() / 2);
              var pageY = e.pageY - ($(window).height() / 2);
              var newvalueX = width * pageX * -1 - 50;
              var newvalueY = height * pageY * -1 - 100;
              $('.background-image').css("background-position", newvalueX+"px     "+newvalueY+"px");
    });

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
                        $scope.loggedIn = true;
                    	$scope.cities = [];
                        $scope.loadForecastDays();

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

    $scope.logout = () => {
        $rootScope.welcomeShowed = true;
        $rootScope.boardsShowed = false;
        $scope.boards = [];
        $scope.user = {};
        $scope.selectedIdBoard = 0;
        $scope.selectedCities = [];
        $scope.cities = [];
        $scope.socket = null;
        $scope.showAll = true;
        $scope.showEditBoardButton = [];
        $scope.tmpBoardName = "";
        $scope.loggedIn = false;
    };

    $scope.loginEvent = e => {
        // If enter key was pressed
        if(e && e.which === 13){
            // Call login function
            $scope.login();
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
                angular.forEach($scope.cities, city => {
                    city.checked=false;
                });
                callback();
			}
		});
    };

    $scope.addCities = () => {
        if($scope.selectedCities.length>0){
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
        }
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

    $scope.getCities = (board, boards) => {

        // Gets status of all cities in a board
        mainService.getCitiesByIdBoard(board.id, response => {
			
			// Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
            	// Creates the board object and pushes it to our boards array
        		board.cities = response.data;
        		$scope.addBoardCities(board);
        		
        		boards.splice(boards.indexOf(board),1);
        		
        		if(boards.length>0){
        			$scope.getCities(boards[0], boards);
        		} else {
        			$rootScope.boardsShowed = true;
        		}
            }
        });

    };

    $scope.selectBoard = idBoard => {
    	const cities = angular.element(".chkCities");
        $scope.selectedIdBoard = idBoard;
    };

    $scope.showBoards = userName => {
        // Calls the getBoards function of the boards controller
        mainService.getBoards(userName, response => {
			// Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {

				const boards = response.data;
				$scope.boards = [];
                for(let i = 0; i < boards.length;i++){
                    $scope.showEditBoardButton[i] = true;
                }
				$scope.getCities(boards[0], boards);
            	
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
                $scope.boards.push({"id":parseInt(response.data.id), "iduser":0, "cities":[], "name":response.data.name});
                $scope.showEditBoardButton.push(true);
                //$scope.showBoards($scope.user.name);
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
            $scope.socket = io.connect('http://34.238.121.215:9000');
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


    $scope.selectCity = idCity => {
        idCity = parseInt(idCity);
        const idx = $scope.selectedCities.indexOf(idCity);
        // If found, it is removed
        if(idx>-1) {
            $scope.selectedCities.splice(idx, 1);
            $scope.cities[idCity-1].checked = false;
        } else {
            // If not, it is added
            $scope.selectedCities.push(idCity);
            $scope.cities[idCity-1].checked = true;
        }

    };    

    $scope.selectCheckboxes = () => {
        angular.forEach(angular.element(".chkCities"), chkElement =>{
            $scope.selectCity(chkElement.value);
        });
    };

    $scope.editBoardName = ($event, $index) => {
        $event.stopPropagation();
        $scope.showEditBoardButton[$index] = false;
        $scope.tmpBoardName = $scope.boards[$index].name;
        $scope.focusOn("boardName"+$scope.boards[$index].id, 0);
    };

    $scope.changeBoardName = ($event, $index) => {
        $event.stopPropagation();
        $scope.showEditBoardButton[$index] = true;
        mainService.changeBoardName($scope.boards[$index], response => {            
            // Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
               ngNotify.set("Name changed!", response.status);
            }
        });
    };

    $scope.cancelChangeBoardName = ($event, $index) => {
        $event.stopPropagation();
        $scope.boards[$index].name = $scope.tmpBoardName;
        $scope.showEditBoardButton[$index] = true;
    };

    $scope.changeCancelBoardName = ($event, $index) => {
        // When enter is pressed
        if($event.keyCode === ENTER_KEY) {
            $scope.changeBoardName($event, $index);
        }
    };

    $scope.focusOn = (id, ms) => {
        $timeout(() => {angular.element("#"+id).focus()}, ms);
    };

    $scope.findForecastByDay = idx => {
        ngNotify.set("Getting forecast...", "info");
        $scope.extendedForecastCities = [];

        // Gets status of all cities in a board by date
        mainService.findForecastByDay(
            $scope.selectedIdBoard,
            $scope.forecastDays[idx], 
            response => {            
            // Got an error
            if(response.status !== "success") {
                ngNotify.set(response.data, response.status);
            } else {
                $scope.extendedForecastCities = response.data;
                ngNotify.set("Done!", "success");
            }
        });
    };

    $scope.loadForecastDays = () => {
        var date = new Date()
        $scope.forecastDays = [];
        for(let i = 1; i < 6; i ++) {
            date.setDate(date.getDate()+1);
            $scope.forecastDays.push(DAY_NAMES[date.getDay()]);
        }
    };

}]);