package de.codecentric.ebss.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.ebss.service.InMemoryOrderEntryService;
import de.codecentric.ebss.service.OrderEntryService;

@Configuration 
public class ServiceConfiguration {

	@Autowired
	private ObjectMapper mapper;
	
	@Bean
	public OrderEntryService orderEntryService(){
		return new InMemoryOrderEntryService(mapper);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
}