package de.codecentric.ebss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaConfig {

    @Value("${kafka.topic:" + OrderApplication.ORDER_CREATED_EVENT + "}")
    private String topic;

	@Value("${kafka.address:192.168.99.100:32777}")
	private String brokerAddress;

	@Value("${zookeeper.address:192.168.99.100:32776}")
	private String zookeeperAddress;

    OrderKafkaConfig() {
    }

    public OrderKafkaConfig(String t, String b, String zk) {
        this.topic = t;
        this.brokerAddress = b;
        this.zookeeperAddress = zk;
    }

    public String getTopic() {
        return topic;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public String getZookeeperAddress() {
        return zookeeperAddress;
    }
}