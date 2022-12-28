package com.lewis.consumer.listener;


import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Configuration
public class TestListener {

    @KafkaListener(topics = "topic-1",groupId = "group-1")
    public void Listen(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition)
    {
        System.out.println("Topic: " + topic + "  partition: " + partition + "  message: " + message);
    }




    //It’s important to have these headers because imagine an listener application where
    // it’s get data from more than one topic and to know what topic is publishing the
    // message the get the header
    @KafkaListener(topics = "topic-1",groupId = "group-1")
    public void Listen2(String message, @Header(KafkaHeaders.RECEIVED_TOPIC)String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition)
    {
         if (topic == "topic-1")
         {
             System.out.println("Received topic 1: " + message);
         }
         else
         {
             System.out.println("Received another topic: " + message);
         }
    }


    //each partition has your own store of counting messages consumed, so this store is called offset.

    //    It’s like two queues, even though the people in the queue go out
    //    then enter new people each queues will have it’s own counting (store)
    //    in the final of the day how many people enter in the day in the queue.

    @KafkaListener(topics = "topic-1",groupId = "group-1")
    public void Listen3(String message, ConsumerRecordMetadata metadata)
    {
        System.out.println("Topic " + metadata.topic() + " partition: " + metadata.partition() + " Message: " + message + " Offset: " + metadata.offset());
        System.out.println("TimesTamp: " + LocalDateTime.ofInstant(
                Instant.ofEpochMilli(metadata.timestamp()), TimeZone.getDefault().toZoneId()
        )); //will compare this timezone with the defined in the controller of Publisher project  before publishing the message

    }


}
