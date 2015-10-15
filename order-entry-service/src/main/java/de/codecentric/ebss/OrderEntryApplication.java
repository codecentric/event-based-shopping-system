package de.codecentric.ebss;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.ebss.config.ApplicationConfiguration;

@Configuration
@Import(ApplicationConfiguration.class)
@EnableAutoConfiguration
public class OrderEntryApplication {

    public static final String ORDER_CREATED_EVENT = "OrderCreatedEvent";

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        SpringApplication.run(OrderEntryApplication.class, args);
    }
}
