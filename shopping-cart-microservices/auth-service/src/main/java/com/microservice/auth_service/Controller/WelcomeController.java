package com.microservice.auth_service.Controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class WelcomeController {

    @GetMapping
    public String hello(){
        return "Hello welcome";
    }
}
