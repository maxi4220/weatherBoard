/*
	Description: This class is meant to be the start point where our stand alone application starts.
	History: Class created: 10/17/2017 - Maximiliano Pozzi
*/
package weatherBoard;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Application {
	
    @Autowired
    private DispatcherServlet servlet;
    public static void main(String[] args)  throws FileNotFoundException, IOException {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CommandLineRunner getCommandLineRunner(ApplicationContext context) {
        servlet.setThrowExceptionIfNoHandlerFound(true);
        return args -> {};
    }
}