package com.Blog.Controller;

import com.Blog.DTO.BlogActualizarDTO;
import com.Blog.DTO.BlogDTO;
import com.Blog.DTO.DeleteBlogDTO;
import com.Blog.Entity.Blog;
import com.Blog.repository.BlogRepository;
import com.Blog.service.BlogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody BlogDTO blogDTO){
        blogService.DataBlog(blogDTO);
        return new ResponseEntity<>("token" , HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public List<Blog> findall(){
        return blogService.GetBlog();
    }

    @GetMapping("/buscarTodo")
    public List<Blog>buscar(){
        return blogRepository.findAll();
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<?> upDate(@RequestBody BlogActualizarDTO actualizarDTO){
        Blog blog=  blogRepository.getReferenceById(actualizarDTO.id());
        blog.Update(actualizarDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> Delete(@RequestBody DeleteBlogDTO delateBlog){
        Blog blog = blogRepository.getReferenceById(delateBlog.id());
        blogRepository.delete(blog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
