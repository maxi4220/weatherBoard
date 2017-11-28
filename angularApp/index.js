"use strict";
// app.js
const express = require('express');  
const app = express();  
const server = require('http').createServer(app);  
const io = require('socket.io')(server);


const port = 9000;

var citiesStatus = [];

app.use(express.static(__dirname + "/app"));



app.get('/', function (req, res) {
	res.sendFile( './index.html');
});

io.on("connection", client => {  
	console.log('Client connected...');

	client.on("citiesStatus", citiesStatus => {
		console.log(citiesStatus);
	});


});

server.listen(port, '0.0.0.0');
console.log("App listening on port " + port);


const request = require('request');
const mysql = require('mysql');

var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "castillo3",
	database: "weatherBoard"
});

var savedCities = [];


var i = 0;

// Connects to our mysql database
con.connect( err => {
	if (err) throw err;

	/*const to = setInterval(() => {

		// Gets status of all cities
		request.get("https://query.yahooapis.com/v1/public/yql?q=select item.condition, atmosphere from weather.forecast where woeid in (468739,466861,466862,332469,465663,332471,466882,332474,467039,466869)&format=json",
			{},
			(error, response, body) => {
				if (!error && response.statusCode === 200) {
					body = JSON.parse(body);

		        	// Saves into cities object the recieved result
		        	const cities = body.query.results.channel;

		        	var update = "";
		        	var paramCity = {};
		        	var whereClause = {};
		        	var id = 1;



		        	for (let city of cities){

		        		paramCity = {
		        			"humidity": parseInt(city.atmosphere.humidity)+parseInt(i),
		        			"pressure": city.atmosphere.pressure,
		        			"temp": city.item.condition.temp,
		        			"text": city.item.condition.text
		        		};
		        		whereClause = {
		        			"id": id
		        		};

		        			// Formats the update clause
		        			update = mysql.format( 'UPDATE cities SET ? WHERE ?', [paramCity, whereClause]);
		        			con.query(update, (err, cities) => {
		        				if (err) throw err;
		        			});

		        			if(citiesStatus.indexOf(id)){
		        				paramCity.id = id;
		        				savedCities.push(paramCity);
		        			}

		        			id++;
		        		}


		        		io.emit("citiesStatus", JSON.stringify(savedCities));
		        	}
		        }
		        );

		if(i>100){
			clearInterval(to);
		}
		i++;
		console.log(i);
		
	}, 2000);*/
});