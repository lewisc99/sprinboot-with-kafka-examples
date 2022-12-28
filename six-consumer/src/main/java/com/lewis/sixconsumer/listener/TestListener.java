package com.lewis.sixconsumer.listener;


import com.lewis.sixconsumer.custom.PersonCustomListener;
import com.lewis.sixconsumer.models.City;
import com.lewis.sixconsumer.models.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

@Configuration
public class TestListener {


    // @KafkaListener(topics = "person-topic",groupId = "group-1", containerFactory = "personKafkaListenerContainerFactory")
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
    }


    @KafkaListener(groupId = "group-1", topics = "city-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void createCity(City city, ConsumerRecordMetadata metadata)
    {
        System.out.println(" partition 1-9: " + metadata.partition() + " City: " + city.getName() + " UF: "+ city.getUF() );
        System.out.println("----------------------------------------------------------------------------");

    }


}
