package com.Blog.Entity;

import com.Blog.DTO.BlogActualizarDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_blog")
    private Long id_blog;
    private String title;
    private String body;
    private String author;
    private String state;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "post_user", joinColumns =
    @JoinColumn(name = "id_blog", referencedColumnName = "id_blog"),inverseJoinColumns =
    @JoinColumn(name = "id_user",referencedColumnName = "id_user"))
    private List<UserEntity>id_user = new ArrayList<>();


    public Long getId_blog() {
        return id_blog;
    }

    public void setId_blog(Long id_blog) {
        this.id_blog = id_blog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<UserEntity> getId_user() {
        return id_user;
    }

    public void setId_user(List<UserEntity> id_user) {
        this.id_user = id_user;
    }

    public Blog(Long id_blog, String title, String body, String author, String state, List<UserEntity> id_user) {
        this.id_blog = id_blog;
        this.title = title;
        this.body = body;
        this.author = author;
        this.state = state;
        this.id_user = id_user;
    }

    public Blog() {
    }

    public void Update(BlogActualizarDTO actualizarDTO){
        if (actualizarDTO.title()!=null){
            this.title= actualizarDTO.title();
        }
        if (actualizarDTO.body()!=null){
            this.body = actualizarDTO.body();
        }
    }
}
