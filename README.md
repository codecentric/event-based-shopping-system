## Event-Based-Shopping-System (EBSS)

This project is a proof of concept for an event-based-shopping-system (EBSS) using Spring Boot and Apache Kafka. As a result of an article by Tobias Flohre, we want to implement the event based system described in the blog. For more information I suggest you read up the article first.

- Blog article by Tobias Flohre https://blog.codecentric.de/2015/09/wer-microservices-richtig-macht-braucht-keine-workflow-engine-und-kein-bpmn/
- Inspiration: Movie Database https://github.com/tobiasflohre/movie-database
- Apache Kafka http://kafka.apache.org/

## Overview

The final infrastructure will look like the following diagram:

![Planned Shopping System](https://blog.codecentric.de/files/2015/08/BPMNVsMicroservices.png "Event Based Shopping System")

## Getting started

You can use docker to get Kafka and Zookeeper up and running, or install the tools manually.

### Automatically install Apache Kafka via docker

Clone repository:

```
git clone git@github.com:wurstmeister/kafka-docker.git
cd kafka-docker
```

Start a cluster:

```
docker-compose up -d
```

Add more brokers:

```
docker-compose scale kafka=3
```

Destroy a cluster:

```
docker-compose stop
```

### Manually install Apache Kafka

Download code

```
wget http://mirror.netcologne.de/apache.org/kafka/0.8.2.0/kafka_2.10-0.8.2.0.tgz
tar -xzf kafka_2.10-0.8.2.0.tgz
cd kafka_2.10-0.8.2.0
```

Start Zookeeper

```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka server

```
bin/kafka-server-start.sh config/server.properties
```

### Use Kafka & Zookeeper

Before you can use Kafka and Zookeeper you need to figure out the right ports. Use *docker-compose ps* to find the dynamically created ports for all docker containers.

Each Spring Boot App contains a application.properties under *src/resources*. It is important that you update the port to match your local infrastructure, before using the Spring Boot Apps.

```
~/git/kafka-docker$ docker-compose ps
         Name                        Command               State                          Ports                        
----------------------------------------------------------------------------------------------------------------------
kafkadocker_kafka_1       /bin/sh -c start-kafka.sh        Up      0.0.0.0:32777->9092/tcp                             
kafkadocker_kafka_2       /bin/sh -c start-kafka.sh        Up      0.0.0.0:32778->9092/tcp                             
kafkadocker_kafka_3       /bin/sh -c start-kafka.sh        Up      0.0.0.0:32779->9092/tcp                             
kafkadocker_zookeeper_1   /bin/sh -c /usr/sbin/sshd  ...   Up      0.0.0.0:32776->2181/tcp, 22/tcp, 2888/tcp, 3888/tcp
```

Step 1: Create a topic

Let's create a topic named "test" with a single partition and only one replica:

```
bin/kafka-topics.sh --create --zookeeper 192.168.99.100:32776 --replication-factor 1 --partitions 1 --topic test
```

We can now see that topic if we run the list topic command:

```
bin/kafka-topics.sh --list --zookeeper 192.168.99.100:32770
test
```

Step 2: Run the produces to send some messages

Run the producer and then type a few messages into the console to send to the server.

```
bin/kafka-console-producer.sh --broker-list 192.168.99.100:32777 --topic test
This is a message
This is another message
```

Step 3: Start a consumer

```
bin/kafka-console-consumer.sh --zookeeper 192.168.99.100:32776 --topic test --from-beginning
This is a message
This is another message
```

For more details see http://kafka.apache.org/documentation.html#quickstart

## Maven projects

To build all Maven projects please run:

```
mvn clean install
```
