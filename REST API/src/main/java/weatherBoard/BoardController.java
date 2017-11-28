/*
	Description: Model class representing a record of Cities table
	History: Class created: 11/17/2017 - Maximiliano Pozzi
*/
package weatherBoard;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class BoardController {

	/*
	 * Description: Adds cities to a specific board
	 * Parameters: 
	 * 		@idBoard: id of desired board. 
	 * 		@cities: array of idCity to associate to a board
	 * History: Method created: 11/19/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping("/addCitiesToBoard")
    public Response addCitiesToBoard(
    		@RequestParam(value="idBoard") byte idBoard, 
    		@RequestParam(value="cities") byte[] cities) throws Exception {
   		return BoardModel.addCitiesToBoard(idBoard, cities);
    }
	/*
	 * Description: Removes cities from a specific board
	 * Parameters: 
	 * 		@idBoard: id of desired board. 
	 * 		@cities: array of idCity to delete from a board
	 * History: Method created: 11/19/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping("/removeCitiesFromBoard")
    public Response removeCitiesFromBoard(
    		@RequestParam(value="idBoard") byte idBoard, 
    		@RequestParam(value="cities") byte[] cities) throws Exception {
   		return BoardModel.removeCitiesFromBoard(idBoard, cities);
    }
	/*
	 * Description: Get boards of a user
	 * Parameters: 
	 * 		@userName: name of the user
	 * History: Method created: 11/23/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping("/getBoardsByUserName")
    public Response getBoardsByUserName(
    		@RequestParam(value="userName") String userName) throws Exception {
   		return BoardModel.getBoardsByUserName(userName);
    }
	/*
	 * Description: Get boards of a user
	 * Parameters: 
	 * 		@userName: name of the user
	 * History: Method created: 11/26/2017
	 */
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping("/addBoard")
    public Response addBoard(
    		@RequestParam(value="userName") String userName) throws Exception {
   		return BoardModel.addBoard(userName);
    }
	
}