//package com.gaenari.backend.domain.messagequeue;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EventPublisher {
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    public void publishEvent(String topic, Object event) {
//        kafkaTemplate.send(topic, event);
//        System.out.println("Event published to " + topic);
//    }
//}