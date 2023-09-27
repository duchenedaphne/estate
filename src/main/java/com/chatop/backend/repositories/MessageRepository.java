package com.chatop.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.backend.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
}
