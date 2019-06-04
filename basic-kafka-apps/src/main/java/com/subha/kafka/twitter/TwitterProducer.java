
//STEPS:
//        1. CREATE A TWITTER CLIENT
//        2. CREATE A KAFKA PRODUCER
//        2. SEND TWEETS TO KAFKA


package com.subha.kafka.twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {

    String consumerKey = "W6LuNdB0Jzqb9GOWaEEnF3YXE";
    String consumerSecret = "BmYoW7r01A9mC4n24QRIOtS4nDfPhfHoao5tyji0WmOz6pfNO6";
    String token = "2864168632-WY5RNqiVzHLceAg7EAAkEHNGpGdcnFusWZIATup";
    String secret = "A9aCyIO8q4ogjWhmJmNxfhZwzoXttz9lY5tD4jS6lkTb1";
    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());


    public Client createTwitterClient(BlockingQueue<String> msgQueue) {
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        List<String> terms = Lists.newArrayList("blockchain", "cryptography");
        hosebirdEndpoint.trackTerms(terms);

        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        Client hosebirdClient = builder.build();
        return hosebirdClient;
    }

    public KafkaProducer<String, String> createKafkaProducer() {
        Properties properties =  new Properties();
        String bootstrapServers = "127.0.0.1:9092";
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }

    private void run() {

        //Twitter Setup
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100);
        final Client client = createTwitterClient(msgQueue);
        client.connect();
        logger.info("connection established!");

        //Kafka Producer
        final KafkaProducer<String, String> kafkaProducer = createKafkaProducer();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                logger.info("shutting down application");
                client.stop();
                kafkaProducer.close();
            }
        });


        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                client.stop();
            }
            if(msg != null) {
                logger.info(msg);
                kafkaProducer.send(new ProducerRecord<String, String>("twitter_tweets", msg), new Callback() {
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e != null) {
                            logger.error("something is wrong: "+ e);
                        }
                    }
                });
            }
        }

        logger.info("Application is done!");
    }

    public static void main(String[] args) {
        new TwitterProducer().run();
    }




}
