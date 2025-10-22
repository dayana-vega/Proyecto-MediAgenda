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

/**
 * Clase para manejar excepciones a nivel global de la aplicación.
 * Convierte errores de validación (@RutChile, @NotBlank, etc.)
 * en respuestas HTTP 400 Bad Request claras.
 */
@RestControllerAdvice // Indica que esta clase manejará excepciones globalmente
public class GlobalExceptionHandler {

    /**
     * Captura la excepción que ocurre cuando falla la validación de un argumento de método
     * (como el objeto Usuario en el Controller) y devuelve un 400.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        
        // Itera sobre todos los errores de validación
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT) // El código 409 es ideal para conflictos de recursos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        
        Map<String, String> errors = new HashMap<>();
        String rootCause = ex.getRootCause().getMessage();

        // 💡 Lógica para identificar qué campo falló (Rut o Email)
        if (rootCause.contains("Duplicate entry") && rootCause.contains("'rut'")) {
            errors.put("rut", "El RUT ingresado ya está registrado en el sistema.");
        } else if (rootCause.contains("Duplicate entry") && rootCause.contains("'email'")) {
            errors.put("email", "El correo electrónico ya está en uso.");
        } else {
            // Error genérico si no podemos identificar el campo
            errors.put("error", "Error de integridad de datos. Posiblemente un valor duplicado.");
        }
        
        return errors;
    }
}