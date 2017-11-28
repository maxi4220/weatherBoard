'use strict';

//service style, probably the simplest one
weatherBoardApp.service("loginService", [ "$http", function($http) {
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
}])