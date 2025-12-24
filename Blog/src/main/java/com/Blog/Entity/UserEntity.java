package com.Blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor*/
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id_user;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "id_user",referencedColumnName = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_role",referencedColumnName = "id_role"))
    private List<Role>role=new ArrayList<>();



    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public UserEntity(Long id_user, String username, String password, List<Role> role) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserEntity() {
    }
}
