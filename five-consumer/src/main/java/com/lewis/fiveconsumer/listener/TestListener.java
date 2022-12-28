package com.lewis.fiveconsumer.listener;


import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

@Configuration
public class TestListener {



    //the producer defined the topic "my-topic" with two partitions    TopicBuilder.name("my-topic").partitions(2).build()
    // but we defined below two listeners with the first listen to partition 0 and second with partition 1-9
    /// so the messages will be divated in half because it's only two partitions

    @KafkaListener(groupId = "my-group", topicPartitions = {@TopicPartition(topic = "my-topic", partitions = "0")})
    public void listenPartition(String message, ConsumerRecordMetadata metadata)
    {
        System.out.println(" partition 0: " + metadata.partition() + " Message: " + message );
        System.out.println("----------------------------------------------------------------------------");

    }


    @KafkaListener(groupId = "my-group", topicPartitions = {@TopicPartition(topic = "my-topic", partitions = "1-9")})
    public void listenPartition2(String message, ConsumerRecordMetadata metadata)
    {
        System.out.println(" partition 1-9: " + metadata.partition() + " Message: " + message );
        System.out.println("----------------------------------------------------------------------------");

    }

}
