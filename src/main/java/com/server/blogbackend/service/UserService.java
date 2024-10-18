package com.server.blogbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.blogbackend.Entity.User;
import com.server.blogbackend.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    UserRepo userrepo;
    public User findOneByEmail(String email) {
        return userrepo.findOneByEmail(email);
    }
    public User findById(Long id) {
        return userrepo.findById(id).orElse(null);
    }
    public User save(User u) {
        return userrepo.save(u);
    }
}
