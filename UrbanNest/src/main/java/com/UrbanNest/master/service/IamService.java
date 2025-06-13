package com.UrbanNest.master.service;

import com.UrbanNest.master.dto.LoginRequest;
import com.UrbanNest.master.dto.LoginResponse;
import com.UrbanNest.master.dto.PersonDto;
import com.UrbanNest.master.dto.ResetPasswordRequest;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;

public interface IamService {

    String addUser(PersonDto user, ResetPasswordRequest password);

    void resetPassword(PersonDto user, String newPassword);

    Optional<PersonDto> findByEmail(String email);

    LoginResponse getAccessToken(LoginRequest authRequest);


    String logout();
}
