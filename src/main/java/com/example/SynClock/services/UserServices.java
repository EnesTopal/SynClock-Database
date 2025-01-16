package com.example.SynClock.services;

import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.User;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserDTO> createAccount(CreateUserDTO userRequest) {
        User newUser = new User();
        newUser.setUsername(userRequest.getUserName());
        newUser.setUserpassword(userRequest.getUserPassword());
        newUser.setGroups(new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(newUser));
    }

    public ResponseEntity<String> deleteAccount (Long uuid){
        Optional<User> userOptional = userRepository.findById(uuid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Success: User and associated data deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found: No user matching the given ID was found.");
    }
}
