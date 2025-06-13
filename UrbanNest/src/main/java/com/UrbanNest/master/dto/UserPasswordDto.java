package com.UrbanNest.master.dto;

import lombok.Data;

@Data
public class UserPasswordDto {
    private PersonDto person;
    private ResetPasswordRequest password;
}
