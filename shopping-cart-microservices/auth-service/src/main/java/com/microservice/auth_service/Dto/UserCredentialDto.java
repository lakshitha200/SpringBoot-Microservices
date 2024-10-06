package com.microservice.auth_service.Dto;


import lombok.Data;

@Data
public class UserCredentialDto {

    private Long userId;
    private String username;
    private String email;
    private String password;
}
