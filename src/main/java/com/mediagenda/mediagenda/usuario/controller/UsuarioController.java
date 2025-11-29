package com.mediagenda.mediagenda.usuario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.mediagenda.mediagenda.exceptions.NotFoundException;
import com.mediagenda.mediagenda.usuario.model.Usuario;
import com.mediagenda.mediagenda.usuario.model.Paciente;
import com.mediagenda.mediagenda.usuario.model.Administrador;
import com.mediagenda.mediagenda.usuario.model.Medico;
import com.mediagenda.mediagenda.usuario.service.UsuarioService;
import com.mediagenda.mediagenda.enums.RolUsuario;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getUsuarioByEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.findByEmail(email);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", usuario.getId());
            response.put("rut", usuario.getRut());
            response.put("nombre", usuario.getNombre());
            response.put("apellido", usuario.getApellido());
            response.put("email", usuario.getEmail());
            response.put("rol", usuario.getRol().toString());
            
            if (usuario instanceof Medico) {
                Medico medico = (Medico) usuario;
                response.put("especialidad", medico.getEspecialidad());
            } else if (usuario instanceof Paciente) {
                Paciente paciente = (Paciente) usuario;
                LocalDate fecha = paciente.getFechaNacimiento();
                if (fecha != null) {
                    response.put("fechaNacimiento", fecha.toString());
                }
            }
            
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); 
        }
    }


    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody Map<String, Object> usuarioData) {
        try {
            if (!usuarioData.containsKey("rol") || usuarioData.get("rol") == null) {
                return new ResponseEntity<>("El campo 'rol' es obligatorio", HttpStatus.BAD_REQUEST);
            }
            
            String rolStr = (String) usuarioData.get("rol");
            RolUsuario rol;
            
            try {
                rol = RolUsuario.valueOf(rolStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Rol inválido. Use MEDICO o PACIENTE", HttpStatus.BAD_REQUEST);
            }
            
            Usuario usuario;
            
            if (rol == RolUsuario.PACIENTE) {
                Paciente paciente = new Paciente();
                
                if (usuarioData.containsKey("fechaNacimiento") && usuarioData.get("fechaNacimiento") != null) {
                    String fechaStr = (String) usuarioData.get("fechaNacimiento");
                    try {
                        paciente.setFechaNacimiento(LocalDate.parse(fechaStr));
                    } catch (Exception e) {
                        return new ResponseEntity<>("Formato de fecha inválido. Use YYYY-MM-DD", HttpStatus.BAD_REQUEST);
                    }
                }
                
                usuario = paciente;
                
            } else if (rol == RolUsuario.MEDICO) {
                Medico medico = new Medico();
                
                if (usuarioData.containsKey("especialidad") && usuarioData.get("especialidad") != null) {
                    medico.setEspecialidad((String) usuarioData.get("especialidad"));
                }
                
                usuario = medico;
                
            } else if (rol == RolUsuario.ADMIN) {
                Administrador admin = new Administrador();
                usuario = admin;
            } else {
                return new ResponseEntity<>("Rol no soportado", HttpStatus.BAD_REQUEST);
            }
            
            usuario.setRut((String) usuarioData.get("rut"));
            usuario.setNombre((String) usuarioData.get("nombre"));
            usuario.setApellido((String) usuarioData.get("apellido"));
            usuario.setEmail((String) usuarioData.get("email"));
            usuario.setContrasenia((String) usuarioData.get("contrasenia"));
            usuario.setRol(rol);
            
            String resultado = usuarioService.crearUsuario(usuario);
            
            if (resultado.contains("ya se encuentra creado")) {
                return new ResponseEntity<>(resultado, HttpStatus.CONFLICT);
            }
            
            return new ResponseEntity<>(resultado, HttpStatus.CREATED);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al crear usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String rut) {
        try {
            String resultado = usuarioService.eliminarUsuario(rut);

            if (resultado.contains("Usuario no existe")) {
                return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
            }
            
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medicos")
    public ResponseEntity<List<Usuario>> listarMedicos() {
        return ResponseEntity.ok(usuarioService.listarMedicos());
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.mostrarUsuarios());
    }
}