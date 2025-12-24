package com.Blog.service;

import com.Blog.Entity.Role;
import com.Blog.Entity.UserEntity;
import com.Blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //es parte del codigo es para optener los roles de los usuarios
    public Collection<GrantedAuthority> mapAutorites(List<Role>roles){
        return roles.stream().map(rol->new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toSet());

    }
    //este es para autentificar los usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("usuario no encontrado"));
        //regresa el nombre de usuario contraseña y el rol
        return new User(users.getUsername(),users.getPassword(),mapAutorites(users.getRole()));
    }
}
