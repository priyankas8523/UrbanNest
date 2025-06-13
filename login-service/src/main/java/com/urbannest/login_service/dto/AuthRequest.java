package com.urbannest.login_service.dto;


import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}

