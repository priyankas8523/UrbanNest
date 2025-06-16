package com.UrbanNest.master.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // HTTP Status Codes
    NOT_FOUND,
    CREATED,

    // Login
    INVALID_CREDENTIALS,
    LOGIN_FAILED,
    INVALID_REFRESH_TOKEN,

    INTERNAL_ERROR,

    //Unit
    UNIT_CREATED


}
