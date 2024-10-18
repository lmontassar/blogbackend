package com.server.blogbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.blogbackend.Entity.User;
import com.server.blogbackend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userservice;
    @Autowired
    private BCryptPasswordEncoder bc;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User u){
        try{
            User user = userservice.findOneByEmail(u.getEmail());
            if( user == null ){
                u.setPassword(bc.encode(u.getPassword()));
                userservice.save(u);
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }catch(Exception e ){
            return ResponseEntity.badRequest().build();
        }
    }


}
