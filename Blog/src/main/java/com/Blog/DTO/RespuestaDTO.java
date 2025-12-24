package com.Blog.DTO;

import lombok.Data;

import java.util.Objects;

//@Data
public class RespuestaDTO {

    private String token;
    private String Bearer_Token="Bearer ";


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer_Token() {
        return Bearer_Token;
    }

    public void setBearer_Token(String bearer_Token) {
        Bearer_Token = bearer_Token;
    }

    public RespuestaDTO(String bearer_Token) {
        Bearer_Token = bearer_Token;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaDTO that = (RespuestaDTO) o;
        return Objects.equals(token, that.token) && Objects.equals(Bearer_Token, that.Bearer_Token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, Bearer_Token);
    }
}
