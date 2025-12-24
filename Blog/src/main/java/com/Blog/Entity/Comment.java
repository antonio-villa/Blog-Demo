package com.Blog.Entity;

import jakarta.persistence.*;

import java.util.List;

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long id_comment;
    private String comment;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "key_comment", joinColumns =
    @JoinColumn(name = "id_comment",referencedColumnName = "id_comment"),
            inverseJoinColumns = @JoinColumn(name = "id_blog",referencedColumnName = "id_blog"))
    private Blog id_blog;
    private String author;
}
