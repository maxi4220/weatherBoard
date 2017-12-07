package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import weatherBoard.Response;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RestControllerAdvice
public class AppErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;
    
    @RequestMapping("/error")
    public Response handleError(HttpServletRequest request, HttpServletResponse response) {

    	RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> reqErr = errorAttributes.getErrorAttributes(requestAttributes, false);
        return new Response("error", reqErr.get("message"));
    }

	@Override
	public String getErrorPath() {
		return null;
	}
}