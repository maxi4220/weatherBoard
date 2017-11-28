'use strict';

//service style, probably the simplest one
weatherBoardApp.service("mainService", [ "$http", function($http) {
    this.user = {
        loggedIn: false,
        name: "",
        id: -1
    };
    this.login = (userName, callback) => {
        $http.get(`http://localhost:8080/login?userName=${userName}`)
        .then(
            response => {
                callback(response.data)
            },
            response => {
                callback(response.data)
            }
        )
    }
    this.getBoards = (userName, callback) => {

        $http.get(`http://localhost:8080/getBoardsByUserName?userName=${userName}`)
        .then(
            response => {
                callback(response.data)
            },
            response => {
                callback(response.data)
            }
        )
    };
    this.addBoard = (userName, callback) => {
        $http.get(`http://localhost:8080/addBoard?userName=${userName}`)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };
    this.loadCities = callback => {
        $http.get("http://localhost:8080/cities")
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };
    this.addCities = (idBoard, cities, callback) => {
        $http.get(`http://localhost:8080/addCitiesToBoard?idBoard=${idBoard}&cities=${cities}`)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };
    this.removeCity = (idBoard, idCity, callback) => {
        $http.get(`http://localhost:8080/removeCitiesFromBoard?idBoard=${idBoard}&cities=${idCity}`)
        .then(
            response => {
                callback(response.data);
            },
            response => {
                callback(response.data)
            }
        )
    };
    this.getCitiesByIdBoard = (idBoard, callback) => {
        $http.get(`http://localhost:8080/cities?idBoard=${idBoard}`)
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