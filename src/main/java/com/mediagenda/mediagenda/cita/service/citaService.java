package com.mediagenda.mediagenda.cita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.usuario.repository.UsuarioRepository;

@Service
public class citaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

}
