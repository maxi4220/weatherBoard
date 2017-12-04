'use strict';

//service style, probably the simplest one
weatherBoardApp.service("mainService", [ "$http", function($http) {
    this.user = {
        loggedIn: false,
        name: "",
        id: -1
    };

    this.login = (userName, callback) => {
        $http.get(`http://localhost:8080/login/${userName}`)
        .then(
            response => {
                callback(response.data)
            },
            response => {
                callback(response.data)
            }
        )
    }
    // Gets all boards of user by user name
    this.getBoards = (userName, callback) => {
        $http.get(`http://localhost:8080/boards/${userName}`)
        .then(
            response => {
                callback(response.data)
            },
            response => {
                callback(response.data)
            }
        )
    };

    // Adds a new board to a specific user
    this.addBoard = (userName, callback) => {
        //${userName}
        $http.post(`http://localhost:8080/boards/${userName}`)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };

    // Changes a board's name
    this.changeBoardName = (board, callback) => {
        $http.put("http://localhost:8080/boards", board)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };

    // Gets all cities
    this.loadCities = callback => {
        $http.get("http://localhost:8080/cities/0")
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };

    // Adds cities to a board
    this.addCities = (idBoard, cities, callback) => {
        var jsonCities = [];
        for(let i = 0; i < cities.length; i++){
            var city = {"id": cities[i]};
            jsonCities.push(city);
        }
        $http.post(`http://localhost:8080/cities/${idBoard}`, jsonCities)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };

    // Removes a city from a board
    this.removeCity = (idBoard, idCity, callback) => {
        $http.delete(`http://localhost:8080/boards/${idBoard}/${idCity}`)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };

    // Gets all cities of a board
    this.getCitiesByIdBoard = (idBoard, callback) => {
        $http.get(`http://localhost:8080/cities/${idBoard}`)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };
    this.getBoardByIdBoard = (idBoard, boards) => {
        for(let i = 0; i < boards.length; i++){
            if(boards[i].id===idBoard){
                return i;
            }
        }
    };

}]);