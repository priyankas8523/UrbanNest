package com.UrbanNest.master.exception;

import com.UrbanNest.master.enums.ResponseCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class UrbanNestException extends Exception{

    private Exception exception;
    private ResponseCode errorCode;
    private String[] fields;

    public UrbanNestException(Exception exception){
        super(exception.getLocalizedMessage());
        this.errorCode = ResponseCode.INTERNAL_ERROR;
        this.exception = exception;
    }

    public UrbanNestException(ResponseCode code, String message, String... fields){
        super(message);
        this.errorCode = code;
        this.fields = fields;
    }
    
    public UrbanNestException(ResponseCode code, String message){
        super(message);
        this.errorCode = code;
    }
    
    //@SuppressWarnings("unused")
    public String[] getMissingFields() {
        return fields;
    }
}
