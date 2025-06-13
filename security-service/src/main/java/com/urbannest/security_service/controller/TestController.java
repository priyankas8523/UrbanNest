package com.urbannest.security_service.controller;

//import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "Welcome!" + request.getSession().getId();
    }
}
