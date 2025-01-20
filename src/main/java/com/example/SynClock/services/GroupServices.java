package com.example.SynClock.services;

import com.example.SynClock.model.DTOs.CreateGroupDTO;
import com.example.SynClock.model.DTOs.GroupDTO;
import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import com.example.SynClock.repositories.GroupRepository;
import com.example.SynClock.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServices {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserTokenServices userTokenServices;

    public GroupServices(GroupRepository groupRepository,
                         UserRepository userRepository,
                         UserTokenServices userTokenServices) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userTokenServices = userTokenServices;
    }

    public ResponseEntity<GroupDTO> createGroup(CreateGroupDTO groupRequest) {
        Group newGroup = new Group();
        Long userId = userTokenServices.validateAndExtractUserId();
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        newGroup.setGroupName(groupRequest.getGroupName());
        newGroup.setAlarmClocks(new ArrayList<>());
        newGroup.getUsers().add(creator);
        creator.getGroups().add(newGroup);
        groupRepository.save(newGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GroupDTO(newGroup));
    }

    public ResponseEntity<String> deleteGroup(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            groupRepository.delete(group);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Success: Group and associated data deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Group not found: No group matching the given ID was found.");
    }

    public ResponseEntity<String> joinGroup(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            Long userId = userTokenServices.validateAndExtractUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            if (group.getUsers().contains(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already a member of the group.");
            }
            group.getUsers().add(user);
            user.getGroups().add(group);
            groupRepository.save(group);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User successfully added to the group.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found with ID: " + groupId);
        }
    }

    public ResponseEntity<String> leaveGroup(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            Long userId = userTokenServices.validateAndExtractUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            group.getUsers().remove(user);
            user.getGroups().remove(group);
            if (group.getUsers().isEmpty()) {
                groupRepository.delete(group);
            } else {
                groupRepository.save(group);
            }
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User successfully left the group.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found with ID: " + groupId);
        }
    }

    public ResponseEntity<List<Group>> getMyGroups() {
        Long userId = userTokenServices.validateAndExtractUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        List<Group> myGroups = user.getGroups();
        return ResponseEntity.status(HttpStatus.OK).body(myGroups);
    }
}
