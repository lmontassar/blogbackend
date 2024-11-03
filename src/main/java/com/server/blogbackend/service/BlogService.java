package com.server.blogbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.blogbackend.Entity.Blog;
import com.server.blogbackend.repository.BlogRepo;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepo mainRepo;

    public Blog createBlog(Blog blog) {
        return mainRepo.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return mainRepo.findAll();
    }

    public Blog getBlogById(Long id) {
        return mainRepo.findById(id).orElse(null);
    }
    public Blog updateBlog(Long id, Blog blogDetails) {
        return null;
    }


    public void deleteBlog(Long id) {
        mainRepo.deleteById(id);
    }

}
