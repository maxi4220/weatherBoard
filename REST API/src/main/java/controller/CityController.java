package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.City;
import model.CityForecast;
import repository.CityRepository;
import weatherBoard.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@ControllerAdvice
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;

	@CrossOrigin(origins = {"http://localhost:9000", "http://34.227.148.48:9000"})
    @RequestMapping(value = "/cities/{idBoard}")
    public Response findCitiesByIdBoard(
    		@PathVariable("idBoard") Long idBoard) {
   		try {
   			
   			List<City> cities;

   			cities = cityRepository.findByBoards_Id(idBoard);
	    	
   			return new Response("success", cities);
   		} catch (Exception ex) {
    		return new Response("error", ex.getMessage());      			
   		}
    	
    }

	@CrossOrigin(origins = {"http://localhost:9000", "http://34.227.148.48:9000"})
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public Response findAll() {
   		try {
	    	List<City> cities = cityRepository.findAll();
	    	
	    	if(cities.isEmpty()) {
	    		return new Response("warn", cities);
	    	} else {
	   			return new Response("success", cities);
	    	}
   		} catch (Exception ex) {
    		return new Response("error", ex.getMessage());      			
   		}
    	
    }
	
	// Consumes the yahoo weather service and returns a list of cities with their status by day
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.227.148.48:9000"})
    @RequestMapping(value = "/cities/{idBoard}/{day}")
	private Response findForecastByDay(
			@PathVariable("idBoard") Long idBoard, 
			@PathVariable("day") String day) {
		try {
			List<CityForecast> respCities = new ArrayList<CityForecast>();
			String strCitiesId = "";
			CityForecast newCity;
			
			List<City> cities = cityRepository.findByBoards_Id(idBoard);
			
			for(City city : cities) {
				strCitiesId += city.getWoeid() + ",";
			}
			strCitiesId = strCitiesId.substring(0, strCitiesId.length()-1);
			String query = "select * from weather.forecast where woeid in ("+strCitiesId+")";
			
			String url = "https://query.yahooapis.com/v1/public/yql?q="+java.net.URLEncoder.encode(query, "UTF-8")+"&format=json";
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JSONObject jsonData = new JSONObject(response.toString());
			
			JSONArray jsonForecast;
			JSONObject objFC;
			if(cities.size()>1) {
				JSONArray jsonCities = jsonData.getJSONObject("query").getJSONObject("results").getJSONArray("channel");
				for(int i = 0; i < jsonCities.length(); i++){
					
					newCity = new CityForecast();
					jsonForecast = jsonCities.getJSONObject(i).getJSONObject("item").getJSONArray("forecast");
					
					for(int j = 0; j < jsonForecast.length(); j++) {
						objFC = jsonForecast.getJSONObject(j);
						if(objFC.getString("day").toString().trim().equalsIgnoreCase(day.trim())) {
							newCity.setName(cities.get(i).getName());
							newCity.setMaxTemp(objFC.getInt("high"));
							newCity.setMinTemp(objFC.getInt("low"));
							newCity.setText(objFC.getString("text"));
							break;
						}
					}
					
					respCities.add(newCity);
				}
			} else {
				JSONObject jsonCities = jsonData.getJSONObject("query").getJSONObject("results").getJSONObject("channel");

				newCity = new CityForecast();
				jsonForecast = jsonCities.getJSONObject("item").getJSONArray("forecast");
				
				for(int j = 0; j < jsonForecast.length(); j++) {
					objFC = jsonForecast.getJSONObject(j);
					if(objFC.getString("day").toString().trim().equalsIgnoreCase(day.trim())) {
						newCity.setName(cities.get(0).getName());
						newCity.setMaxTemp(objFC.getInt("high"));
						newCity.setMinTemp(objFC.getInt("low"));
						newCity.setText(objFC.getString("text"));
						break;
					}
				}
				
				respCities.add(newCity);
			}
			
			
			
			return new Response("success", respCities);
		} catch (Exception ex) {
			return new Response("error", ex.getMessage());
		}
	}
}