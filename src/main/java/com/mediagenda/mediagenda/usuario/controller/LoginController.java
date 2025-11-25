package com.mediagenda.mediagenda.usuario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mediagenda.mediagenda.usuario.dto.LoginDTO;
import com.mediagenda.mediagenda.usuario.model.Usuario;
import com.mediagenda.mediagenda.usuario.model.Paciente;
import com.mediagenda.mediagenda.usuario.model.Medico;
import com.mediagenda.mediagenda.usuario.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        try {
            if (loginDTO.getEmail() == null || loginDTO.getEmail().trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "El email es obligatorio");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            if (loginDTO.getContrasenia() == null || loginDTO.getContrasenia().trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "La contraseña es obligatoria");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            Usuario usuario = usuarioService.findByEmail(loginDTO.getEmail());
            
            if (!usuario.getContrasenia().equals(loginDTO.getContrasenia())) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Credenciales incorrectas");
                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login exitoso");
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", usuario.getId());
            userData.put("rut", usuario.getRut());
            userData.put("nombre", usuario.getNombre());
            userData.put("apellido", usuario.getApellido());
            userData.put("email", usuario.getEmail());
            userData.put("rol", usuario.getRol().toString());
            
            if (usuario instanceof Medico) {
                Medico medico = (Medico) usuario;
                userData.put("especialidad", medico.getEspecialidad());
            } else if (usuario instanceof Paciente) {
                Paciente paciente = (Paciente) usuario;
                LocalDate fecha = paciente.getFechaNacimiento();
                if (fecha != null) {
                    userData.put("fechaNacimiento", fecha.toString());
                }
            }
            
            response.put("usuario", userData);
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Credenciales incorrectas");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Map<String, Object>> verificarEmail(@PathVariable String email) {
        try {
            usuarioService.findByEmail(email);
            Map<String, Object> response = new HashMap<>();
            response.put("existe", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("existe", false);
            return ResponseEntity.ok(response);
        }
    }
}