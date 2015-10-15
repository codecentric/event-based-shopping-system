package de.codecentric.ebss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;

import de.codecentric.ebss.config.ServiceConfiguration;

@EnableIntegration
@SpringBootApplication
@Import(ServiceConfiguration.class)
public class CommoditiesReservationApplication {

    public static final String ORDER_CREATED_EVENT = "OrderCreatedEvent";

    public static void main(String[] args) {
        SpringApplication.run(CommoditiesReservationApplication.class, args);
    }
}
