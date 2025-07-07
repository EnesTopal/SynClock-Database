package com.example.SynClock.services;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.repositories.GroupRepository;
import com.example.SynClock.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserServices(UserRepository userRepository,
                        GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public ResponseEntity<UserDTO> createAccount(CreateUserDTO userRequest) {
        User newUser = new User();
        newUser.setUsername(userRequest.getUsername());
        newUser.setUserpassword(userRequest.getUserpassword());
        newUser.setGroups(new ArrayList<>());
        User savedUser = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(savedUser));
    }

    public ResponseEntity<ApiResponse<String>> deleteAccount (Long uuid){
        Optional<User> userOptional = userRepository.findById(uuid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Group> myGroups = user.getGroups();
            userRepository.delete(user);
            for (Group group : myGroups) {
                List<User> groupMembers = group.getUsers();
                if (groupMembers.size() == 1) {
                    groupRepository.delete(group);
                }
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("Success: User and associated data deleted successfully."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("User not found: No user matching the given ID was found."));
    }

    public User getOneUserByUserName(String Username) {
        return userRepository.findByUsername(Username);
    }
}
