package com.mediagenda.mediagenda.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice 
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        
        Map<String, String> errors = new HashMap<>();
        String rootCause = ex.getRootCause().getMessage();

        if (rootCause.contains("Duplicate entry") && rootCause.contains("'rut'")) {
            errors.put("rut", "El RUT ingresado ya está registrado en el sistema.");
        } else if (rootCause.contains("Duplicate entry") && rootCause.contains("'email'")) {
            errors.put("email", "El correo electrónico ya está en uso.");
        } else {
            errors.put("error", "Error de integridad de datos. Posiblemente un valor duplicado.");
        }
        
        return errors;
    }
}