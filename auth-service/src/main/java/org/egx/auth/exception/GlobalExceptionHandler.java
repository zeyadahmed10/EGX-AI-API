package org.egx.auth.exception;


import exceptions.ResourceExistedException;
import exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResourceExistedException.class})
    protected ResponseEntity<?> handleConflict(ResourceExistedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<?> handleConflict(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<?> handleConflict(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex) {
        int statusCode = Integer.parseInt(ex.getMessage().substring(ex.getMessage().length()-3));
        var httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(ex.getMessage(), httpStatus);
    }
}
