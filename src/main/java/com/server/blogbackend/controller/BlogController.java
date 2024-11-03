package com.server.blogbackend.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.blogbackend.Entity.Blog;
import com.server.blogbackend.service.BlogService;

import io.jsonwebtoken.io.IOException;

import java.nio.file.Path;



@Controller
@RequestMapping("/blog")
public class BlogController {
    private final String UPLOAD_DIR = "uploads/";

    @Autowired
    BlogService blogser;

    @PostMapping("/add")
    public ResponseEntity<?> addBlog(@RequestParam("blog") String blogJson ,@RequestParam("file")  MultipartFile file){
        try{
            Blog b = (new ObjectMapper()).readValue(blogJson, Blog.class);
            String imageUrl = uploadFile(file);
            b.setUrl(imageUrl);
            blogser.createBlog(b);
            return ResponseEntity.accepted().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> GetBlogs(){
        try{
            List<Blog> bs = blogser.getAllBlogs();
            return ResponseEntity.accepted().body(bs);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> GetBlogByid(@PathVariable("id") Long id){
        try{
            Blog bs = blogser.getBlogById(id);
            return ResponseEntity.accepted().body(bs);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    // upload an image
    private String uploadFile(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(UPLOAD_DIR);
        try{ 
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch(Exception e){

        }
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = "blog-" + System.currentTimeMillis() + extension;

        Path filePath = uploadPath.resolve(fileName);
        try{
            Files.copy(file.getInputStream(), filePath);
        } catch(Exception e){
        }
        return fileName.toString();
    }
    @GetMapping("/upload/{imageName:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String imageName) throws MalformedURLException {
        Path file = Paths.get("uploads/" + imageName);
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}