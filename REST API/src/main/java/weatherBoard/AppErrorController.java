

package weatherBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RestControllerAdvice
public class AppErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {

    	String strError = "";
    	RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> reqErr = errorAttributes.getErrorAttributes(requestAttributes, false);
    	strError = "{\"error\":\"" + reqErr.get("message") + "\"}";
        return strError;
    }

	@Override
	public String getErrorPath() {
		return null;
	}
}