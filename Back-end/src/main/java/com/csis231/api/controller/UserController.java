package com.csis231.api.controller;

import com.csis231.api.model.User;
import com.csis231.api.service.UserService;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @PostMapping("users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userService.createUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<Long> loginUser(@RequestBody User userCred) {
        String username = userCred.getUsername();
        String password = userCred.getPassword();

        // Perform user authentication logic
        User user = userService.validate(username, password);

        if (user != null) {
            // User authentication successful
            
            return ResponseEntity.status(HttpStatus.FOUND).body(user.getId());
        } else {
            // User authentication failed
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((long)0);
        }
    }
}