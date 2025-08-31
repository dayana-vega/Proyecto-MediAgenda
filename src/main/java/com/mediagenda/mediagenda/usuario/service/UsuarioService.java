package com.mediagenda.mediagenda.usuario.service;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mediagenda.mediagenda.usuario.dto.UsuarioDTO;
import com.mediagenda.mediagenda.usuario.exceptions.NotFoundException;
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



}
