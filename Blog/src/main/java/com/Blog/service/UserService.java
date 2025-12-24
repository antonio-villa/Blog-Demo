package com.Blog.service;

import com.Blog.DTO.RecordDTO;
import com.Blog.Entity.Role;
import com.Blog.Entity.UserEntity;
import com.Blog.repository.RoleUserRepository;
import com.Blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleUserRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleUserRepository repository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = repository;
    }

    public boolean verifyName(String userEntity){
        return userRepository.existsByUsername(userEntity);

    }

    public void DataUser (RecordDTO userEntity){
        UserEntity user = new UserEntity();
        user.setUsername(userEntity.getUsername());
        user.setPassword(encoder.encode(userEntity.getPassword()));
        Role role = roleRepository.findByName("AUTHOR").get();
        user.setRole(Collections.singletonList(role));
        //System.out.println(user.getUsername()+" "+user.getPassword()+" "+user.getRole());
        userRepository.save(user);
    }



}
