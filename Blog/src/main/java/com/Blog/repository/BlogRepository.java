package com.Blog.repository;

import com.Blog.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long> {


    List<Blog> findByAuthor(String author);
}
