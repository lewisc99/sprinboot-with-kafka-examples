package com.lewis.fourconsumer.listener;


import com.lewis.fourconsumer.custom.PersonCustomListener;
import com.lewis.fourconsumer.models.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

@Configuration
public class TestListener {

    @PersonCustomListener(groupId = "group-1")
    public void Listen(Person person, ConsumerRecordMetadata metadata)
    {
        System.out.println("Create Person - Topic: " + metadata.topic() + " partition: " + metadata.partition() + " \n "
                + " Person name: " + person.getName() +" Thread: " + Thread.currentThread().getId());
        System.out.println("------------------------------------------");
    }


    @PersonCustomListener(groupId = "group-2")
    public void historyPerson(Person person, ConsumerRecordMetadata metadata)
    {
        System.out.println("Historic - Topic: "+ metadata.topic() + " partition: "+
                metadata.partition() + " Person: " + person.getName()+" Thread: " + Thread.currentThread().getId());
        System.out.println("------------------------------------------------");
    }



}
