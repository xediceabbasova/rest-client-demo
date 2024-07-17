package com.company.restclientdemo.controller;

import com.company.restclientdemo.dto.response.UserDataResponse;
import com.company.restclientdemo.dto.request.UserRequest;
import com.company.restclientdemo.dto.response.UserCreateResponse;
import com.company.restclientdemo.dto.response.UserResponse;
import com.company.restclientdemo.dto.response.UserUpdateResponse;
import com.company.restclientdemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public UserResponse getListUsers(@RequestParam(defaultValue = "1") int page) {
        return userService.getListUsers(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataResponse> getSingleUser(@PathVariable int id) {
        return userService.getSingleUser(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserUpdateResponse updateUser(@PathVariable int id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
