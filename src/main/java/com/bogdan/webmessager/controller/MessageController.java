package com.bogdan.webmessager.controller;

import com.bogdan.webmessager.model.MessageModel;
import com.bogdan.webmessager.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel messageModel) {
        System.out.println("handling send message: " + messageModel + " to: " + to);
        boolean isExists = UserStorage.getInstance().getUsers().contains(to);
        if (isExists) {
            messagingTemplate.convertAndSend("/topic/messages/" + to, messageModel);
        }
    }
}
