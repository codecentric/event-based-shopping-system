package de.codecentric.ebss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.ebss.controller.ExceptionController;
import de.codecentric.ebss.controller.MoviesController;
import de.codecentric.ebss.kafka.KafkaConfig;
import de.codecentric.ebss.kafka.OrderEntryProducerConfiguration;

@Configuration 
@Import({ServiceConfiguration.class, OrderEntryProducerConfiguration.class, KafkaConfig.class})
public class ControllerConfiguration {
	
	@Value("${server.context-path}")
	private String serverContextPath;
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private OrderEntryProducerConfiguration orderEntryConfig;
	
	@Bean
	public MoviesController moviesController(){
		return new MoviesController(serviceConfiguration.movieService(), orderEntryConfig.producer());
	}
	
	@Bean
	public ExceptionController errorController(){
		return new ExceptionController();
	}
	
}