package com.subha.kafka.twitter;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Consumer.class.getName());

        Properties properties =  new Properties();
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "java-cgi-1";
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //Create Consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

        //Subscribe to a topic
        kafkaConsumer.subscribe(Collections.singleton("twitter_tweets"));
//        kafkaConsumer.subscribe(Arrays.asList("topic1", "topic2"));

        //Poll for new data
        while(true) {
            ConsumerRecords<String, String> consumerRecords =  kafkaConsumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String, String> record: consumerRecords) {
                logger.info("key: "+record.key()+" value: "+record.value());
            }

        }
    }
}
