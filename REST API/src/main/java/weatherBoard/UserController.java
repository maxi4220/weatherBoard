package weatherBoard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RestControllerAdvice
public class UserController {
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping("/login")
    public Response login(@RequestParam(value="userName") String userName) throws Exception {
   		return UserModel.login(userName);
    }
}