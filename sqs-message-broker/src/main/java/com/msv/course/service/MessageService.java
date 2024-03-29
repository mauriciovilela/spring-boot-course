package com.msv.course.service;

import com.msv.course.message.MessageSender;
import com.msv.course.model.Message;
import com.msv.course.model.Order;
import com.msv.course.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void processMessageQueue(String id) {
        ordersService.findById(Long.valueOf(id)).ifPresent(order -> {
            // add message
            messageRepository.save(Message.builder()
                    .creationDate(LocalDateTime.now())
                    .text(id)
                    .build());
        });
    }

}