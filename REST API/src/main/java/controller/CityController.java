package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.CityDAO;
import model.City;
import weatherBoard.Response;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@ControllerAdvice
public class CityController {

	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/cities/{idBoard}", method = RequestMethod.GET)
    public Response getCitiesByIdBoard(@PathVariable("idBoard") byte idBoard) throws Exception {
   		return CityDAO.getCitiesByIdBoard(idBoard);
    }

	/*
	 * Description: Adds cities to a specific board
	 * Parameters: 
	 * 		@idBoard: id of desired board. 
	 * 		@cities: array of idCity to associate to a board
	 * History: Method created: 11/19/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/cities/{idBoard}", method = RequestMethod.POST)
    public Response addCitiesToBoard(
    		@PathVariable("idBoard") byte idBoard, 
    		@RequestBody City[] cities) throws Exception {
   		return CityDAO.addCitiesToBoard(idBoard, cities);
    }
}