package com.mediagenda.mediagenda.usuario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.mediagenda.mediagenda.exceptions.NotFoundException;
import com.mediagenda.mediagenda.usuario.model.Usuario;
import com.mediagenda.mediagenda.usuario.service.UsuarioService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

// Marca la clase como un controlador REST
@RestController
// Define el prefijo base para todas las rutas de este controlador
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    // Inyección de dependencias del servicio (gracias a @RequiredArgsConstructor de Lombok)
    private final UsuarioService usuarioService;

    /**
     * Endpoint para obtener un usuario por su email.
     * Mapeado a GET /api/v1/usuarios/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.findByEmail(email);
            // Retorna 200 OK y el objeto Usuario
            return ResponseEntity.ok(usuario);
        } catch (NotFoundException e) {
            // Si el servicio lanza NotFoundException, retorna 404 Not Found
            // Nota: Se recomienda un @ControllerAdvice para manejar excepciones globalmente.
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); 
        }
    }

    /**
     * Endpoint para crear un nuevo usuario.
     * Mapeado a POST /api/v1/usuarios
     */
    @PostMapping
    // @RequestBody mapea el cuerpo JSON de la petición al objeto Usuario
    public ResponseEntity<String> crearUsuario(@Valid @RequestBody Usuario usuario) {
        String resultado = usuarioService.crearUsuario(usuario);
        
        if (resultado.contains("ya se encuentra creado")) {
            // Si el servicio indica que ya existe, retorna 409 Conflict
            return new ResponseEntity<>(resultado, HttpStatus.CONFLICT);
        }
        
        // Si la creación fue exitosa, retorna 201 Created
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
    
    /**
     * Endpoint para eliminar un usuario.
     * Mapeado a DELETE /api/v1/usuarios
     * Nota: DELETE por cuerpo es menos convencional, DELETE por ID es más común.
     * Adaptado para usar el email/ID en el cuerpo según tu service.
     */
    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String rut) {
        String resultado = usuarioService.eliminarUsuario(rut);

        if (resultado.contains("Usuario no existe")) {
             // Si no existe para eliminar, retorna 404 Not Found
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        
        // Si la eliminación fue exitosa, retorna 200 OK
        return ResponseEntity.ok(resultado);
    }
}

