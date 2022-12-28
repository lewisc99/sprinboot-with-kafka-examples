package com.lewis.thirdconsumer.listener;


import com.lewis.thirdconsumer.models.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

@Configuration
public class TestListener {

    @KafkaListener(topics = "person-topic",groupId = "group-1", containerFactory =
    "personConcurrentKafkaListenerContainerFactory")
    public void Listen(Person person, ConsumerRecordMetadata metadata)
    {
       System.out.println("Topic: " + metadata.topic() + " partition: " + metadata.partition() + " \n "
       + " Person name: " + person.getName());
       System.out.println("------------------------------------------");
    }


    // So when a publisher send a message to
    //the second and third listener will show the same
    // message and not going to divide the messages into partitions, because
    // the groupId is different.
    @KafkaListener(topics = "person-topic", groupId = "group-2", containerFactory =
    "personConcurrentKafkaListenerContainerFactory")
    public void historyPerson(Person person, ConsumerRecordMetadata metadata)
    {
        System.out.println("Topic: "+ metadata.topic() + " partition: "+
                metadata.partition() + " Person: " + person.getName());
        System.out.println("------------------------------------------------");
    }
}
