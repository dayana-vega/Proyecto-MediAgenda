package com.mediagenda.mediagenda.cita.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.usuario.repository.UsuarioRepository;

@Service
public class citaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String crearCita(Cita cita){
        Optional<Cita> citaExistente = citaRepository.findByPacienteRut(cita.getPaciente()).getRut());
         if (citaExistente.isPresent()){
            throw new RuntimeException("El paciente ya tiene una cita registrada")
         }
        
        return;
    }

}
