package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import model.Board;
import model.User;
import repository.BoardRepository;
import repository.UserRepository;
import weatherBoard.Response;

@RestController
@RestControllerAdvice
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	@CrossOrigin(origins = {"http://localhost:9000", "http://34.238.121.215:9000"})
    @RequestMapping(value = "/login/{userName}", method = RequestMethod.GET)
    public Response login(@PathVariable("userName") String userName) {
    	try {
	   		User user = userRepository.findByNameIgnoreCase(userName);
	   		if(user == null) {
	   			user = new User(userName);
	   			userRepository.save(user);

		   		Board board = new Board(user.getId(), "Board");
				boardRepository.save(board);
	    	}
   		
	   		return new Response("success", user.getId());
	   		
    	} catch (Exception ex) {
        	return new Response("error", ex.getMessage());
    	}
    }
	
	
}