package com.lewis.nineconsumer.config;


import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lewis.nineconsumer.models.Person;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;

@Configuration
@EnableKafka
public class ConsumerKafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String,Person> personConsumerFactory()
    {
        var configs = new HashMap<String, Object>();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserialize.class);

        var jsonDeserializer = new JsonDeserializer(Person.class)
                .trustedPackages("*").forKeys();


        return  new DefaultKafkaConsumerFactory(configs, new StringDeserializer(),jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person>
    personKafkaListenerContainerFactory()
    {
        var factory = new ConcurrentKafkaListenerContainerFactory<String,Person>();

        factory.setConsumerFactory(personConsumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler());
      //  factory.setRecordInterceptor(exampleInterceptor());
     //   factory.setCommonErrorHandler(defaultErrorHandler());
        return factory;
    }

    public ProducerFactory<String, Person> deadLetterFactory()
    {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers()); //will get the host of kafka
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializable.class);

        return new DefaultKafkaProducerFactory<>(configs,new StringSerializer(), new JsonSerializer<>());
    }


    private CommonErrorHandler defaultErrorHandler() {

        var recoverer = new DeadLetterPublishingRecoverer(new KafkaTemplate<>(deadLetterFactory()));
        return new DefaultErrorHandler(recoverer,new FixedBackOff(100l,2));
    }


    private RecordInterceptor<String, Person> exampleInterceptor() {

        return new RecordInterceptor<String, Person>() {
            @Override
            public ConsumerRecord<String, Person> intercept(ConsumerRecord<String, Person> consumerRecord) {

                return consumerRecord;
            }
            @Override
            public void success(final ConsumerRecord<String,Person> record,final Consumer<String,Person> consumer)
            {
                System.out.println("Success");
            }

            @Override
            public void failure(ConsumerRecord<String, Person> record, Exception exception, Consumer<String, Person> consumer) {

              System.out.println("Fail");
                RecordInterceptor.super.failure(record, exception, consumer);
            }
        };
    }
}
