package com.example.SynClock.controllers;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.CreateGroupDTO;
import com.example.SynClock.model.DTOs.GroupDTO;
import com.example.SynClock.model.Group;
import com.example.SynClock.services.GroupServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupServices groupServices;

    public GroupController(GroupServices groupServices) {
        this.groupServices = groupServices;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GroupDTO>> createGroup(@RequestBody CreateGroupDTO groupRequest) {
        return groupServices.createGroup(groupRequest);
    }

    @DeleteMapping("/delete/{groupId}")
    public ResponseEntity<ApiResponse<String>> deleteGroup(@PathVariable Integer groupId) {
        return groupServices.deleteGroup(groupId.longValue());
    }

    @PostMapping("/join/{groupId}")
    public ResponseEntity<ApiResponse<String>> joinGroup(@PathVariable Long groupId) {
        return groupServices.joinGroup(groupId);
    }

    @PostMapping("/leave/{groupId}")
    public ResponseEntity<ApiResponse<String>> leaveGroup(@PathVariable Long groupId) {
        return groupServices.leaveGroup(groupId);
    }

    @GetMapping("/my-groups")
    public ResponseEntity<ApiResponse<List<Group>>> getMyGroups() {
        return groupServices.getMyGroups();
    }
}