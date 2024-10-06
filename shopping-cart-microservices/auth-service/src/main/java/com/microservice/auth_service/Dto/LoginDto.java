package com.microservice.auth_service.Dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
