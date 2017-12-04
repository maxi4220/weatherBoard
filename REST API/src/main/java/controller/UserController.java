package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dao.UserDAO;
import weatherBoard.Response;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RestControllerAdvice
public class UserController {
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping(value = "/login/{userName}", method = RequestMethod.GET)
    public Response login(@PathVariable("userName") String userName) throws Exception {
   		return UserDAO.login(userName);
    }
}