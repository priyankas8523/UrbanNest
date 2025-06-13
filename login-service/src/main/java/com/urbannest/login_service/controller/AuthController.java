package com.urbannest.login_service.controller;

import com.urbannest.login_service.dto.AuthResponse;
import com.urbannest.login_service.dto.AuthRequest;
import com.urbannest.login_service.entity.UserEntity;
import com.urbannest.login_service.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

//    @Autowired
//    private UserDetailsService userDetailsService;


    private List<UserEntity> members = new ArrayList<>(List.of(
            new UserEntity(1L,"Tom", "Tom123", "Manager"),
            new UserEntity(2L,"Jerry", "Jerry123", "QA"),
            new UserEntity(3L,"Oggy", "Oggy123", "HR")
    ));

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(authRequest.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @GetMapping("/")
    public String greet(HttpServletRequest rqst){
        return "Hello!  " + rqst.getSession().getId();
    }

    @GetMapping("/member")
    public List<UserEntity> getAll(){
        return members;
    }

    @PostMapping("/member")
    public UserEntity createMember(@RequestBody UserEntity member){
        members.add(member);
        return member;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest rqst){
        return  (CsrfToken) rqst.getAttribute("_csrf");

    }




}
