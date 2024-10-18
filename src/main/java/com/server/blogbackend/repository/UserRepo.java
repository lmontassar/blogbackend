package com.server.blogbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.blogbackend.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
    public User findOneByEmail(String email);
}
