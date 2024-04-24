package com.ivan.projectmanager.controller.advice;

import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(HandleCustomIllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HandleCustomIllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
    }

    @ExceptionHandler(HandleCustomNullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(HandleCustomNullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
    }

    @ExceptionHandler(HandleCustomNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(HandleCustomNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}