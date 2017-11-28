package weatherBoard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RestControllerAdvice
public class CityController {
	@CrossOrigin(origins = {"http://34.205.125.27:9000", "http://localhost:9000"})
    @RequestMapping("/cities")
    public Response getCitiesByIdBoard(@RequestParam(value="idBoard", defaultValue="0") byte idBoard) throws Exception {
   		return CityModel.getCitiesByIdBoard(idBoard);
    }
}