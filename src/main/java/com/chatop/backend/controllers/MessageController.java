package com.chatop.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.jpo.message.MessageRequest;
import com.chatop.backend.jpo.message.MessageResponse;
import com.chatop.backend.services.message.MessageServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SecurityRequirement(name = "spring_oauth")
@PreAuthorize("hasAuthority('SCOPE_read_access')")
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    
    @Autowired
    private MessageServiceImpl messageService;
    
    @Operation(description = "Post message.")
    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
        @RequestBody MessageRequest request
    ) {
        return messageService.buildMessage(request);
    }
}
