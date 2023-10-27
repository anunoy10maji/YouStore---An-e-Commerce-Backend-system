package com.anu.proj.youtech.Exceptions;

import com.anu.proj.youtech.Dtos.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundHandler(ResourceNotFound ex)
    {
        ApiResponseMessage res=ApiResponseMessage.builder().
                message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exp)
    {
        List<ObjectError> allErr=exp.getBindingResult().getAllErrors();
        Map<String,Object> errMap=new HashMap<>();
        allErr.stream().forEach(objectError ->{
            String message=objectError.getDefaultMessage();
            String field=((FieldError) objectError).getField();
            errMap.put(message,field);
        });

        return new ResponseEntity<>(errMap,HttpStatus.EXPECTATION_FAILED);
    }
}
