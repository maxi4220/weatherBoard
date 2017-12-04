/*
	Description: Model class representing a record of Boards table
	History: Class created: 11/17/2017 - Maximiliano Pozzi
*/
package controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dao.BoardDAO;
import model.Board;
import weatherBoard.Response;

@RestController
@RestControllerAdvice
public class BoardController {
	/*
	 * Description: Removes cities from a specific board
	 * Parameters: 
	 * 		@idBoard: id of desired board. 
	 * 		@cities: array of idCity to delete from a board
	 * History: Method created: 11/19/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/boards/{idBoard}/{idCity}", method = RequestMethod.DELETE)
    public Response removeCitieFromBoard(
    		@PathVariable byte idBoard, 
    		@PathVariable byte idCity) throws Exception {
   		return BoardDAO.removeCitiesFromBoard(idBoard, idCity);
    }
	/*
	 * Description: Get boards of a user
	 * Parameters: 
	 * 		@userName: name of the user
	 * History: Method created: 11/23/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/boards/{userName}", method = RequestMethod.GET)
    public Response getBoardsByUserName(
    		@PathVariable String userName) throws Exception {
   		return BoardDAO.getBoardsByUserName(userName);
    }
	/*
	 * Description: Adds a  new board
	 * Parameters: 
	 * 		@userName: name of the user
	 * History: Method created: 11/26/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/boards/{userName}", method = RequestMethod.POST)
    public Response addBoard(
    		@PathVariable String userName) throws Exception {
   		return BoardDAO.addBoard(userName);
    }
	/*
	 * Description: Changes the name of a board
	 * Parameters: 
	 * 		@idBoard: id of the board
	 * History: Method created: 11/30/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/boards", method = RequestMethod.PUT)
    public Response changeBoardName(
    		@RequestBody Board board) throws Exception {
   		return BoardDAO.changeBoardName(board);
    }
	
}