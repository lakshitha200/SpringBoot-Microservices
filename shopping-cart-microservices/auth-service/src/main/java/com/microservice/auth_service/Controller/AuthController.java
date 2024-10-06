package com.microservice.auth_service.Controller;

import com.microservice.auth_service.Dto.LoginDto;
import com.microservice.auth_service.Dto.UserCredentialDto;
import com.microservice.auth_service.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    private String registerUser(@RequestBody UserCredentialDto userCredentialDto) {
        return authService.registerUser(userCredentialDto);
    }

    @PostMapping("/token")
    private String getToken(@RequestBody LoginDto loginDto) {
        return authService.getToken(loginDto);
    }

    @GetMapping("/validate")
    private String validateToken(@RequestParam("token") String token){
        return authService.validateToken(token);
    }


}
