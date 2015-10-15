package de.codecentric.ebss.kafka;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.integration.dsl.kafka.KafkaProducerMessageHandlerSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.dsl.support.Function;
import org.springframework.integration.dsl.support.PropertiesBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import de.codecentric.ebss.model.Address;
import de.codecentric.ebss.model.Order;
import de.codecentric.ebss.model.Recipient;

@Configuration
public class OrderEntryProducerConfiguration {

	@Autowired
	private KafkaConfig kafkaConfig;

	private static final String OUTBOUND_ID = "outbound";

	private Log log = LogFactory.getLog(getClass());
	
	private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

	@Bean
	@DependsOn(OUTBOUND_ID)
	CommandLineRunner kickOff(@Qualifier(OUTBOUND_ID + ".input") MessageChannel in) {
		return args -> {
			for (int i = 0; i < 100; i++) {
				Address address = new Address(Locale.GERMANY.getDisplayCountry(), "Colonge", "50667", "Domkloster", "4");
				Recipient recipient = new Recipient("Alexander", "Mustermann", address, address);
				int amount = ThreadLocalRandom.current().nextInt(1, 15);
				Order bestellung = new Order(amount, "movieId-" + i, recipient);
				String bestellungAsJson = ow.writeValueAsString(bestellung);
				in.send(new GenericMessage<String>(bestellungAsJson));
				log.info("ordering movie with movieId-" + i + " " + bestellungAsJson);
				Thread.sleep(5000);
			}
		};
	}

	@Bean(name = OUTBOUND_ID)
	IntegrationFlow producer() {

		log.info("starting producer flow..");

		return flowDefinition -> {
			Consumer<KafkaProducerMessageHandlerSpec.ProducerMetadataSpec> producerMetadataSpecConsumer = (
					KafkaProducerMessageHandlerSpec.ProducerMetadataSpec metadata) -> metadata
					.async(true).batchNumMessages(5)
					.valueClassType(String.class);

			Consumer<PropertiesBuilder> producerProperties = props -> props
					.put("queue.buffering.max.ms", "15000");
			Function<Message<Object>, ?> messageKey = m -> m.getHeaders().get(
					IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER);
			KafkaProducerMessageHandlerSpec outboundChannelAdapter = Kafka
					.outboundChannelAdapter(producerProperties);
			String topic = this.kafkaConfig.getTopic();
			String brokerAddress = this.kafkaConfig.getBrokerAddress();

			KafkaProducerMessageHandlerSpec messageHandlerSpec = outboundChannelAdapter
					.messageKey(messageKey).addProducer(topic, brokerAddress,
							producerMetadataSpecConsumer);

			flowDefinition.handle(messageHandlerSpec);
		};
	}
}