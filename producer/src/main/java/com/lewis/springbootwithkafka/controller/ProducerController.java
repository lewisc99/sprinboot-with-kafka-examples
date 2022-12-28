package com.lewis.springbootwithkafka.controller;


import com.lewis.springbootwithkafka.models.City;
import com.lewis.springbootwithkafka.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDateTime;

@RestController
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Serializable> jsonKafkaTemplate;

//    private int count;

    @GetMapping("send")
    public ResponseEntity<String> send()
    {

        kafkaTemplate.send("topic-1","ola Mundo");

//        count++;

        return ResponseEntity.ok().build();
    }


    @GetMapping("send-person")
    public void sendPerson()
    {

        System.out.println(LocalDateTime.now());
        jsonKafkaTemplate.send("person-topic",new Person("João"));
    }

    @GetMapping("my-topic")
    public void send2()
    {
        System.out.println(LocalDateTime.now());
        kafkaTemplate.send("my-topic", "test");
    }

    @GetMapping("send-city")
    public void sendCity()
    {
        jsonKafkaTemplate.send("city-topic", new City("Minas Gerais","MG")  );

    }

}
