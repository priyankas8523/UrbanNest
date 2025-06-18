package com.UrbanNest.master.service;

import com.UrbanNest.master.dto.LoginRequest;
import com.UrbanNest.master.dto.LoginResponse;
import com.UrbanNest.master.dto.PersonDto;
import com.UrbanNest.master.dto.ResetPasswordRequest;
import com.UrbanNest.master.entity.PersonEntity;

public interface PersonService {

    //-- Only add person, no username password, iamuser not included-- //
  //  PersonDto addPerson(PersonDto personDto);

    PersonDto addPerson(PersonDto personDto,ResetPasswordRequest password);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    LoginResponse getAccessToken(LoginRequest authRequest);

    String logout();

    //void updateRole(String newRole, String email);
}
