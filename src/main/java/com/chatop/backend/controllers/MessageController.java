package com.chatop.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.jpo.message.MessageRequest;
import com.chatop.backend.jpo.message.MessageResponse;
import com.chatop.backend.services.message.MessageServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@Api("Messages CRUD operations.")
@RequestMapping("/api/messages")
public class MessageController {
    
    @Autowired
    private final MessageServiceImpl messageService;
    
    @ApiOperation("Post message.")
    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
        @RequestBody MessageRequest request
    ) {
        return messageService.buildMessage(request);
    }
}
