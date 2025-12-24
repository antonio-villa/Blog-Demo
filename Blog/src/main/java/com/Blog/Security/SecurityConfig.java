package com.Blog.Config;

import com.Blog.Security.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
}

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JWTFilter jwtFilter(){
        return new JWTFilter();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/blog/create").hasAnyAuthority("AUTHOR","ADMIN")
                .requestMatchers(HttpMethod.GET, "/blog/buscar").hasAnyAuthority("AUTHOR","ADMIN")
                .requestMatchers(HttpMethod.GET, "/blog/buscarTodo").permitAll()
                .requestMatchers(HttpMethod.PUT, "/blog/update").hasAuthority("AUTHOR")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        security.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }
    //AuthenticationManager
}
