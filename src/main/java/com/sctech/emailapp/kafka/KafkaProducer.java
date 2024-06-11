package com.sctech.emailapp.kafka;

import com.sctech.emailapp.model.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, EmailData> kafkaTemplate;

    @Value("${email.app.file.queue.topic}")
    private String fileDataTopic;

    public void sendToQueue(EmailData message){
        kafkaTemplate.send(fileDataTopic, message);
    }
}
