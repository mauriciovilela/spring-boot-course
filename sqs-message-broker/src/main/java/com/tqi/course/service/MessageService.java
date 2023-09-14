package com.tqi.course.service;

import com.tqi.course.message.MessageSender;
import com.tqi.course.model.Message;
import com.tqi.course.model.Order;
import com.tqi.course.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final OrdersService ordersService;
    private final MessageSender messageSender;
    private final MessageRepository messageRepository;

    public void sendMessageAsync() {
        Order order = ordersService.create();
        messageSender.sendMessageAsync(order.getId().toString());
    }

    public void processMessageQueue(String id) {
        // add message
        messageRepository.save(Message.builder()
                .creationDate(LocalDateTime.now())
                .text(id)
                .build());
    }

}