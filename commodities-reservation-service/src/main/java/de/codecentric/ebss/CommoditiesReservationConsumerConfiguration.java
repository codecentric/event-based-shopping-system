package de.codecentric.ebss;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.integration.dsl.kafka.KafkaHighLevelConsumerMessageSourceSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.kafka.support.ZookeeperConnect;

import de.codecentric.ebss.service.OrderEntryService;

@Configuration
public class CommoditiesReservationConsumerConfiguration {

	private Log log = LogFactory.getLog(getClass());

	@Autowired 
	private OrderEntryService orderEntryService;
	
	@Autowired
	private KafkaConfig kafkaConfig;

	@Bean
	IntegrationFlow consumer() {

		log.info("starting consumer..");

		KafkaHighLevelConsumerMessageSourceSpec messageSourceSpec = Kafka
				.inboundChannelAdapter(
						new ZookeeperConnect(this.kafkaConfig
								.getZookeeperAddress()))
				.consumerProperties(
						props -> props.put("auto.offset.reset", "smallest")
								.put("auto.commit.interval.ms", "100"))
				.addConsumer(
						"myGroup",
						metadata -> metadata
								.consumerTimeout(100)
								.topicStreamMap(
										m -> m.put(this.kafkaConfig.getTopic(),
												1)).maxMessages(1)
								.valueDecoder(String::new));

		Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer = e -> e.poller(p -> p.fixedDelay(100));

		return IntegrationFlows
				.from(messageSourceSpec, endpointConfigurer)
				.<Map<String, ConcurrentHashMap<String, String>>> handle(
						(payload, headers) -> {
							payload.entrySet().forEach(
									e -> orderEntryService.createOrderEntryFromJson(e.getValue()));
							return null;
						}).get();
	}
}
