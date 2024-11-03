package com.server.blogbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.blogbackend.Entity.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog,Long> {
    
}
