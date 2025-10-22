package com.mediagenda.mediagenda.usuario.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mediagenda.mediagenda.exceptions.NotFoundException;
import com.mediagenda.mediagenda.usuario.model.Usuario;
import com.mediagenda.mediagenda.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class UsuarioService {

    
    private final UsuarioRepository usuarioRepository;

    public Usuario findByEmail(String email){
        String norm = email == null?"": email.trim();
        return usuarioRepository.findByEmailIgnoreCase(norm)
        .orElseThrow(() -> new NotFoundException("Usuario no existe"));
    }

    public String crearUsuario(Usuario usuario){
        Optional<Usuario> user = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());

        if (user.isPresent()){
            return "Usuario ya se encuentra creado";

        }
        else{
            usuarioRepository.save(usuario);
            return "Usuario creado exitosamente";
        }

    }
    public String eliminarUsuario(String rut){
        Optional<Usuario> user = usuarioRepository.findByRut(rut);

        if (user.isPresent()){
            Usuario usuario = user.get();
            usuarioRepository.deleteById(usuario.getId());
            return "Usuario eliminado";

        }
        else{
            return "Usuario no existe para eliminar";
        }

    }





}
