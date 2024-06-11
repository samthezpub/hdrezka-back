package com.example.hdrezka.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
