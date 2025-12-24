package com.Blog.Controller;

import com.Blog.DTO.LoginDTO;
import com.Blog.DTO.RecordDTO;
import com.Blog.DTO.RespuestaDTO;
import com.Blog.Security.JWTToken;
import com.Blog.repository.UserRepository;
import com.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTToken jwtToken;

    @PostMapping("/record")
    public ResponseEntity<String> Record (@RequestBody RecordDTO recordDTO){
        if (userService.verifyName(recordDTO.getUsername())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.OK);
        }
        userService.DataUser(recordDTO);
        return new ResponseEntity<>("Usuario guardado con exito",HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> Login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtToken.GenerateToken(authentication);
        return new ResponseEntity<>(new RespuestaDTO(token),HttpStatus.OK);
    }

}
