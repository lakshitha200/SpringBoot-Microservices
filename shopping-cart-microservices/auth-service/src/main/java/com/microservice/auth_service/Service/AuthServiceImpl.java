package com.microservice.auth_service.Service;

import com.microservice.auth_service.Dto.LoginDto;
import com.microservice.auth_service.Dto.UserCredentialDto;
import com.microservice.auth_service.Entity.UserCredential;
import com.microservice.auth_service.Repository.UserCredentialRepository;
import com.microservice.auth_service.Utility.JwtUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private UserCredentialRepository userCredentialRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Override
    public String registerUser(UserCredentialDto userCredentialDto) {
        UserCredential userCredential = modelMapper.map(userCredentialDto, UserCredential.class);
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        return "User Credential successfully saved....";
    }

    @Override
    public String getToken(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(authentication);
            return token;
        }  else {
            throw new RuntimeException("invalid user request !");
        }
    }

    @Override
    public String validateToken(String token) {
        if(jwtUtil.validateToken(token)){
            return "Token is valid.";
        }
        return "Token is invalid.";
    }


}
