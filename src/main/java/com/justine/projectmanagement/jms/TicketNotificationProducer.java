package com.justine.projectmanagement.jms;

import com.justine.projectmanagement.model.Comment;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TicketNotificationProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value(value = "${springjms.topicName}")
    private String topicName;

    public void sendNotification(Comment comment) {
        HashMap studentHashMap = new HashMap();
        studentHashMap.put("text", comment.getText());

        jmsTemplate.convertAndSend(topicName, studentHashMap);
    }
}