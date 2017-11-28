'use strict';

//service style, probably the simplest one
weatherBoardApp.service("dataService", [function() {
    this.data = null;

    this.getData = () => {
    	return this.data;
    };
    this.setData = (data) => {
    	this.data = data;
    };
}])