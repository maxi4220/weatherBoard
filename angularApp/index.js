"use strict";
// app.js
const express = require('express');  
const app = express();  
const server = require('http').createServer(app);  
const io = require('socket.io')(server);
const request = require('request');
const mysql = require('mysql');

const port = 9000;
const max_count = 1950;
const minute = 60000;

var citiesStatus = [];
var connectedUsers = 0;
var hndl; // interval handle
var canUseYahooAPI = true;

// Static content
app.use(express.static(__dirname + "/app"));

// Request routing
app.get('/', function (req, res) {
	res.sendFile( './index.html');
});

var con;
var savedCities = [];
var i = 0;

// Hadles client socket connection

io.on("connection", client => {  
	console.log("user connected");
	connectedUsers++;

	// If it is the first client connected
	if(connectedUsers==1&&canUseYahooAPI) {
		console.log("1");
		con = mysql.createConnection({
			host: "localhost",
			user: "root",
			password: "castillo3",
			database: "weatherBoard"
		});

		// Connects to our mysql database
		con.connect( err => {
			if (err) console.log(err)

				hndl = setInterval(() => {
					console.log("getting data from yahoo weather");
				// Gets status of all cities
				request.get("https://query.yahooapis.com/v1/public/yql?q=select item.condition, atmosphere from weather.forecast where woeid in (468739,466861,466862,332469,465663,332471,466882,332474,467039,466869)&format=json",
					{},

					(error, response, body) => {
						if (!error && response.statusCode === 200) {
							body = JSON.parse(body);
							savedCities = [];

				        	// Saves into cities object the recieved result
				        	const cities = body.query.results.channel;

				        	var update = "";
				        	var paramCity = {};
				        	var whereClause = {};
				        	var id = 1;


				        	for (let city of cities){

				        		paramCity = {
				        			"humidity": city.atmosphere.humidity,
				        			"pressure": city.atmosphere.pressure,
				        			"temp": city.item.condition.temp,
				        			"text": city.item.condition.text
				        		};
				        		whereClause = {
				        			"id": id
				        		};

			        			// Formats the update clause
			        			update = mysql.format( 'UPDATE cities SET ? WHERE ?', [paramCity, whereClause]);

			        			// Update the database with new values
			        			con.query(update, (err, cities) => {
			        				if (err) console.log(err);
			        			});

			        			paramCity.id = id;
			        			savedCities.push(paramCity);

			        			id++;
			        		}

			        		// Notifies to the client the cities status changes
			        		io.emit("citiesStatus", JSON.stringify(savedCities));
			        	}
			        }
				);

				if(i>max_count){
					clearInterval(hndl);
					canUseYahooAPI = false;
				}
				i++;
				console.log(i);
				
			}, minute); // Every minute
		});
	}
	
	client.on("disconnect", () => {
		connectedUsers--;
		if(connectedUsers===0){
			clearInterval(hndl);
		}
	});

});

server.listen(port, '0.0.0.0');
console.log("App listening on port " + port);
