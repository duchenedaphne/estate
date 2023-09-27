package com.chatop.backend.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chatop.backend.models.User;
import com.chatop.backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) throws Exception {
        
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }

    @Override
    public User updateUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) throws Exception {
        
        userRepository.deleteById(id);
        return "Le compte a bien été supprimé !";
    }

    @Override
    public User getUserByEmail(String email) throws Exception {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) throws Exception {
        return userRepository.findAll(pageable);
    }
    
    public ResponseEntity<?> fetchUser(Long id) {

        User user = new User();
        HttpStatus status;
        
        try {
            user = getUser(id);
            
        } catch (HttpStatusCodeException exception) {

            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération de la location.", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
