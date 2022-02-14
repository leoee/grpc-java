package com.demo.client.controller;

import com.demo.client.entity.User;
import com.demo.client.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired
  private UserService userService;
  @RequestMapping(value = "/users", method = RequestMethod.POST)
  ResponseEntity<User> createUser(@RequestBody User requestUser) {

    return new ResponseEntity<>(this.userService.createUser(requestUser), HttpStatus.CREATED);
  }
}
