package de.codecentric.ebss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.ebss.controller.ExceptionController;
import de.codecentric.ebss.controller.MoviesController;

@Configuration 
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
	
	@Value("${server.context-path}")
	private String serverContextPath;
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;

	@Bean
	public MoviesController moviesController(){
		return new MoviesController(serviceConfiguration.movieService());
	}
	
	@Bean
	public ExceptionController errorController(){
		return new ExceptionController();
	}
	
}