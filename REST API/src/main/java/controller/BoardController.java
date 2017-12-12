/*
	Description: Model class representing a record of Boards table
	History: Class created: 11/17/2017 - Maximiliano Pozzi
*/
package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import model.Board;
import model.City;
import model.User;
import repository.BoardRepository;
import repository.CityRepository;
import repository.UserRepository;
import weatherBoard.Response;

@RestController
@RestControllerAdvice
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CityRepository cityRepository;
	/*
	 * Description: Adds cities to a specific board
	 * Parameters: 
	 * 		@idBoard: id of desired board. 
	 * 		@cities: array of idCity to associate to a board
	 * History: Method created: 11/19/2017
	 */
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.238.121.215:9000"})
    @RequestMapping(value = "/cities/{idBoard}", method = RequestMethod.POST)
    public Response addCitiesToBoard(
    		@PathVariable("idBoard") Long idBoard, 
    		@RequestBody List<Long> idcities) {
   		try {
   			Board board = boardRepository.findOne(idBoard);
   			City city;
   			List<City> cities = new ArrayList<City>();
   			
   			if(board == null) {
   	    		return new Response("warn", "The board " + idBoard + " does not exist.");      				
   			} else {
   				for(Long idcity : idcities) {
   					city = cityRepository.findOne(idcity);
   					
   					if(city != null) {
   						if(!board.isInCities(city)) {
	   						cities.add(city);
	   						board.addCity(city);
   						}
   					}
   				}
   				boardRepository.save(board);
   				return new Response("success", cities);   				
   			}
   			
   		} catch (Exception ex) {
    		return new Response("error", ex.getMessage());      			
   		}
    }
	/*
	 * Description: Removes cities from a specific board
	 * Parameters: 
	 * 		@idBoard: id of desired board. 
	 * 		@cities: array of idCity to delete from a board
	 * History: Method created: 11/19/2017
	 */
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.238.121.215:9000"})
    @RequestMapping(value = "/boards/{idBoard}/{idCity}", method = RequestMethod.DELETE)
    public Response removeCityFromBoard(
    		@PathVariable Long idBoard, 
    		@PathVariable Long idCity) throws Exception {
		Board board = boardRepository.findOne(idBoard);
		if(board == null) {
			return new Response("warn", "The board was not found.");
		} else {
			City city = cityRepository.findOne(idCity);
			if(city == null) {
				return new Response("warn", "The city was not found.");
			} else {
				board.removeCity(city);
				boardRepository.save(board);
			}
		}
   		return new Response("success", "");
    }
	/*
	 * Description: Get boards of a user
	 * Parameters: 
	 * 		@userName: name of the user
	 * History: Method created: 11/23/2017
	 */
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.238.121.215:9000"})
    @RequestMapping(value = "/boards/{userName}", method = RequestMethod.GET)
    public Response findBoardsByUserName(@PathVariable String userName) {
   		try {
   			User user = userRepository.findByNameIgnoreCase(userName);
   			
   			if(user == null) {
   				return new Response("warn", "The user " + userName + " does not exist.");
   			} else {
   				List<Board> boards = boardRepository.findByIduser(user.getId());
   				
   				if(boards.isEmpty()) {
   					
   	   				return new Response("warn", "The user " + userName + " does not have any board.");   					
   				} else {
   					/*for(Board board : boards) {
   						board.setCities(cityRepository.findByBoards_Id(board.getId()));
   					}*/
   					return new Response("success", boards);
   				}
   			}
   			
   		} catch (Exception ex) {
    		return new Response("error", ex.getMessage());   
   		}
    }
	/*
	 * Description: Adds a  new board
	 * Parameters: 
	 * 		@userName: name of the user
	 * History: Method created: 11/26/2017
	 */
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.238.121.215:9000"})
    @RequestMapping(value = "/boards/{userName}", method = RequestMethod.POST)
    public Response addBoard(@PathVariable String userName) {
   		try {
   			Board board;
   			User user = userRepository.findByNameIgnoreCase(userName);
   			if(user == null) {
   	    		return new Response("warn", "User " + userName + " not found.");
   			} else {
   				board = new Board(user.getId(), "Board");
   				boardRepository.save(board);
   				
   				return new Response("success", board);
   			}
   			
   		} catch (Exception ex) {
    		return new Response("error", ex.getMessage());   
   		}
    }
	/*
	 * Description: Changes the name of a board
	 * Parameters: 
	 * 		@idBoard: id of the board
	 * History: Method created: 11/30/2017
	 */
	@CrossOrigin(origins = {"http://localhost:9000", "http://34.238.121.215:9000"})
    @RequestMapping(value = "/boards/{idBoard}", method = RequestMethod.PUT)
    public Response changeBoardName(
    		@PathVariable Long idBoard,
    		@RequestBody String boardName) {
   		try {
   			Board board = boardRepository.findOne(idBoard);
   			if(board == null) {
   				
   				return new Response("warn", "The given board is not valid.");
   			} else {
   				board.setName(boardName);
   				boardRepository.save(board);
   				return new Response("success", "Board name changed!");
   			}
    		  
   			
   		} catch( Exception ex) {
    		return new Response("error", ex.getMessage());   
   		}
    }
	
}