package com.Blog.service;

import com.Blog.DTO.BlogActualizarDTO;
import com.Blog.DTO.BlogDTO;
import com.Blog.Entity.Blog;
import com.Blog.Entity.UserEntity;
import com.Blog.Security.JWTFilter;
import com.Blog.Security.JWTToken;
import com.Blog.repository.BlogRepository;
import com.Blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class BlogService  {

    private HttpServletRequest request;
    private JWTToken jwtToken;
    private UserRepository userRepository;
    private BlogRepository blogRepository;

    public BlogService(HttpServletRequest request, JWTToken jwtToken, UserRepository userRepository, BlogRepository blogRepository) {
        this.request = request;
        this.jwtToken = jwtToken;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    public String optenerToken(){
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token)&&token.startsWith("Bearer ")){
            return token.substring(7,token.length());
        }
        return null;
    }

    public void DataBlog(BlogDTO blogDTO){
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setBody(blogDTO.getBody());
        String username = jwtToken.extractUsername(optenerToken());
        blog.setAuthor(username);
        blog.setState("PUBLISHED");
        UserEntity user = userRepository.findByUsername(username).get();
        blog.setId_user(Collections.singletonList(user));
        blogRepository.save(blog);
    }


    public List<Blog> GetBlog(){
        String author = jwtToken.extractUsername(optenerToken());
        return blogRepository.findByAuthor(author);
    }

}
