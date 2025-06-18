package com.UrbanNest.master.controller;

import com.UrbanNest.master.dto.*;
import com.UrbanNest.master.service.serviceImpl.PersonServiceImpl;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master")
public class PersonController {

    private final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test endpoint works!");
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/person")
    public ResponseEntity<String> createPerson(@RequestBody UserPasswordDto userPasswordDto){
        personService.addPerson(userPasswordDto.getPerson(), userPasswordDto.getPassword());
        System.out.println("cont----------------------------------------------------------------------------------------------------------------------------");
        return ResponseEntity.ok("Person created!");
    }

    // -- Only add person, no username password, iamuser not included -- //
//    @PostMapping("/person")
//    public ResponseEntity<String> createPerson(@RequestBody PersonDto personDto){
//        personService.addPerson(personDto);
//        return ResponseEntity.ok("Person created!");
//    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        personService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok("Password updated successfully!!!");
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> getAccessToken(
            @Valid @RequestBody LoginRequest authRequest) {

        LoginResponse loginResponse = personService.getAccessToken(authRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok(personService.logout());
    }
}
