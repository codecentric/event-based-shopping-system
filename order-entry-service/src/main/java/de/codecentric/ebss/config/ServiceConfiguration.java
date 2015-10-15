package de.codecentric.ebss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.ebss.service.InMemoryMovieService;
import de.codecentric.ebss.service.MovieService;

@Configuration 
public class ServiceConfiguration {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Bean
	public MovieService movieService(){
		return new InMemoryMovieService(objectMapper);
	}
	
}