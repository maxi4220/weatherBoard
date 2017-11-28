'use strict';

//service style, probably the simplest one
weatherBoardApp.service("boardsService", ["ngNotify", function(ngNotify) {
    this.showBoards = user => {
        ngNotify.set(`Hey ${user.name}!`, "info");
        return [{id:"1","name":"Ciudad autónoma de Buenos Aires", "humidity":"50"}];
    };
}])