package com.Blog.Security;

import io.jsonwebtoken.*;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTToken {

    public static final long timeExpired=300000;
    @Value("${api.security.secret}")
    private String apiSecret;

    //genera el token
    public String GenerateToken(Authentication authentication){
        Date time = new Date();
        Date expired =new Date(time.getTime()+timeExpired);
        String token = Jwts.builder()
                //nombre del usuario
                .setSubject(authentication.getName())
                //fecha de creacion
                .setIssuedAt(time)
                //tiempo de expiracion
                .setExpiration(expired)
                //tipo de algoritmo
                .signWith(SignatureAlgorithm.HS512,apiSecret).compact();
        return token;
    }

    public String extractUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(apiSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(apiSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e){
            throw new AuthenticationCredentialsNotFoundException("El token es nulo o vacio");
        }
    }

}
