package com.hrbl.gts.coreservices.statementpathapi.exception;


import com.hrbl.gts.coreservices.statementpathapi.model.ErrorDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;


@RestControllerAdvice
public class AppExceptionHandler {
    //@Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,  WebRequest webRequest) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails("E", errors.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }


    @ExceptionHandler(NoDataFoundException.class)
    public HttpEntity<ErrorDetails> handleNoDataFoundException(NoDataFoundException noDataFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails("E", noDataFoundException.getMessage());
        System.out.println(noDataFoundException.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public HttpEntity<ErrorDetails> handleNoDataFoundException(Exception ex, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails("E", ex.getMessage());
        System.out.println(ex.toString());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }
}
