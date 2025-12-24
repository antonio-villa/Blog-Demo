package com.Blog.Security;

import com.Blog.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private JWTToken jwtToken;

    //optiene es tokem apartier del bearer
    public String GetToken(HttpServletRequest request){
        String bareToken= request.getHeader("Authorization");
        if(StringUtils.hasText(bareToken)&&bareToken.startsWith("Bearer ")){
            return bareToken.substring(7,bareToken.length());
        }
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String doToken = GetToken(request);
        //valida si hay un token y valida el token
        if(StringUtils.hasText(doToken) && jwtToken.validToken(doToken)){
            //extrae el token
            String username = jwtToken.extractUsername(doToken);
            //carga el usuario
            UserDetails userDetails = securityService.loadUserByUsername(username);
            //mapea los roles
            List<String> Roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            if (Roles.contains("ADMIN") || Roles.contains("AUTHOR")){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);

    }
}
