package com.UrbanNest.master.exception;

import com.UrbanNest.master.dto.Response;
import com.UrbanNest.master.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private Response buildResponse(ResponseCode code, String message, String errorDetails, WebRequest request){
        return Response.builder()
                .code(code)
                .message(message)
                .errorDetails(errorDetails)
                .path(request.getContextPath())
                .requestId(UUID.randomUUID().toString())
                .errors(null)
                .version("1.0")
                .build();
    }
    
    @ExceptionHandler(UrbanNestException.class)
    protected ResponseEntity<Object> handleMissingValueException(UrbanNestException urbanNestException, WebRequest webRequest){
    
        log.error("Exception Occurred!!!!!!!", urbanNestException.getMessage());
        String errorDetails = (urbanNestException.getMissingFields() != null && urbanNestException.getMissingFields().length > 0)
                ? "Missing fields: " + String.join(", ", urbanNestException.getMissingFields())
                : null;
        
        Response response = buildResponse(
                urbanNestException.getErrorCode(),
                urbanNestException.getMessage(),
                errorDetails,
                webRequest
        );
        
        return ResponseEntity.badRequest().body(response);
    
    
    }
}
