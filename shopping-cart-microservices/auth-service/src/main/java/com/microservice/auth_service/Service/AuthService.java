package com.microservice.auth_service.Service;

import com.microservice.auth_service.Dto.LoginDto;
import com.microservice.auth_service.Dto.UserCredentialDto;

public interface AuthService {

    String registerUser(UserCredentialDto userCredentialDto);

    String getToken(LoginDto loginDto);

    String validateToken(String token);
}
