package com.chatop.backend.services.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chatop.backend.models.Message;

public interface MessageService {
    
    public Message createMessage(Message message) throws Exception;
    
    public Message getMessage(Long id) throws Exception;
    
    public Message updateMessage(Message message) throws Exception;

    public String deleteMessage(Long id) throws Exception;

    public Page<Message> getAllMessages(Pageable pageable) throws Exception;
}
