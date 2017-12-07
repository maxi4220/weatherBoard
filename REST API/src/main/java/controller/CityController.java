package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.City;
import repository.CityRepository;
import weatherBoard.Response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@ControllerAdvice
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;

	@CrossOrigin(origins = {"http://localhost:9000", "http://34.205.125.27:9000"})
    @RequestMapping(value = "/cities/{idBoard}")
    public Response findCitiesByIdBoard(@PathVariable("idBoard") Long idBoard) {
   		try {
	    	List<City> cities = cityRepository.findByBoards_Id(idBoard);
	    	
   			return new Response("success", cities);
   		} catch (Exception ex) {
    		return new Response("error", ex.getMessage());      			
   		}
    	
    }
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.205.125.27:9000"})
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
}