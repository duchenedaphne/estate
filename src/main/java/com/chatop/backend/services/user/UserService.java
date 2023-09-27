package com.chatop.backend.services.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chatop.backend.models.User;

public interface UserService {
    
    public User createUser(User user) throws Exception;

    public User getUser(Long id) throws Exception;

    public User updateUser(User user) throws Exception;

    public String deleteUser(Long id) throws Exception;

    public User getUserByEmail(String email) throws Exception;

    public Page<User> getAllUsers(Pageable pageable) throws Exception;
}
