package com.app.security.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class  JwtAuthenticationRequest implements Serializable {

    private String username;
    private String email;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String email, String password) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
    }
}
