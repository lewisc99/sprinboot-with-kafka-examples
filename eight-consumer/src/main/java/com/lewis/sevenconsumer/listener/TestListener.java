package com.lewis.sevenconsumer.listener;


import com.lewis.sevenconsumer.custom.PersonCustomListener;
import com.lewis.sevenconsumer.models.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

@Configuration
public class TestListener {


    @PersonCustomListener(groupId = "group-1")
    public void createPerson(Person person, ConsumerRecordMetadata metadata)
    {
        System.out.println("create Person");
        System.out.println("Topic " + metadata.topic() + " partition: " + metadata.partition() + " Person: " + person.getName()
                + " Thread: "+ Thread.currentThread().getId());
        System.out.println("--------------------------------------------------------------------");
    }

    @PersonCustomListener(groupId = "group-2")
    // @KafkaListener(topics = "person-topic",groupId = "group-2", containerFactory = "personKafkaListenerContainerFactory")
    public void historyPerson(Person person, ConsumerRecordMetadata metadata)
    {
        System.out.println("historic listeneer");
        System.out.println("Topic " + metadata.topic() + " partition: " + metadata.partition() + " Person: " + person.getName()
                + " Thread: "+ Thread.currentThread().getId());
        System.out.println("--------------------------------------------------------------------");

        throw new IllegalArgumentException("fail listener");
    }


}
