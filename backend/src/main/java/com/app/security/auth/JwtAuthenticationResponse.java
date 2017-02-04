package com.app.security.auth;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return String.format("{\"token\":\"%s\"}", this.token);
    }

}
