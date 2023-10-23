package com.csis231.api.service;


import com.csis231.api.model.User;
import com.csis231.api.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User validate(String username, String password) {
        User user = userRepository.validateUser(username, password);
        if (user != null) {
            // User login successful
            // Perform the necessary actions after successful login
            // For example, generate an access token, store user information in session, etc.
            return user;
        } else {
            // User login failed
            // Handle the error and return an appropriate response
            return null;
        }
    }
}