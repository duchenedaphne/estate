package com.chatop.backend.services.message;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chatop.backend.jpo.message.MessageRequest;
import com.chatop.backend.jpo.message.MessageResponse;
import com.chatop.backend.models.Message;
import com.chatop.backend.models.Rental;
import com.chatop.backend.models.User;
import com.chatop.backend.repositories.MessageRepository;
import com.chatop.backend.services.rental.RentalServiceImpl;
import com.chatop.backend.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private final UserServiceImpl userServiceImpl;
    @Autowired
    private final RentalServiceImpl rentalServiceImpl;

    @Override
    public Message createMessage(Message message) throws Exception {
        return messageRepository.save(message);
    }

    @Override
    public Message getMessage(Long id) throws Exception {
        
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            return messageOptional.get();
        } else {
            throw new RuntimeException("Aucune message trouvé.");
        }
    }

    @Override
    public Message updateMessage(Message message) throws Exception {
        return messageRepository.save(message);
    }

    @Override
    public String deleteMessage(Long id) throws Exception {
        messageRepository.deleteById(id);
        return "Le message a bien été supprimé";
    }

    @Override
    public Page<Message> getAllMessages(Pageable pageable) throws Exception {
        return messageRepository.findAll(pageable);
    }
    
    public ResponseEntity<MessageResponse> buildMessage(MessageRequest messageRequest) {
        
        HttpStatus status;
        User user;
        Rental rental;
        Message message = new Message();
        MessageResponse messageResponse = new MessageResponse();

        message.setMessage(messageRequest.getMessage());
        message.setCreated_at(new Date());
        message.setUpdated_at(new Date());

        try { 
            user = userServiceImpl.getUser(messageRequest.getUser_id());
            message.setUser_id(user);
            rental = rentalServiceImpl.getRental(messageRequest.getRental_id());
            message.setRental_id(rental);
            createMessage(message);

        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            messageResponse.setMessage("Échec de l'envoi du message.");
            return new ResponseEntity<MessageResponse>(messageResponse, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        messageResponse.setMessage("Le message a bien été envoyé.");
        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
    }
}
