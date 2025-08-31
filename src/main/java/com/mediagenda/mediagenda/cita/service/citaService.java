package com.mediagenda.mediagenda.cita.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.horario.repository.HorarioRepository;
import com.mediagenda.mediagenda.horario.service.HorarioService;
import com.mediagenda.mediagenda.usuario.repository.UsuarioRepository;

@Service
public class CitaService {

    private final HorarioService horarioService;

    private final HorarioRepository horarioRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    CitaService(HorarioRepository horarioRepository, HorarioService horarioService) {
        this.horarioRepository = horarioRepository;
        this.horarioService = horarioService;
    }

    //CREAR CITA 
    public Cita creaCita(Cita cita){
        HorarioMedico horarioMedico = HorarioRepository.findByMedicoIdAndDia( 
            cita.getMedico().getId(),
            cita.getFechaHora().getDayOfWeek()
        ).orElseTrow(() -> new RuntimeException('El médico no atiende ese día'));

    //TRAER TODO EL LISTADO DE CITAS
    public List<Cita> obtenerTodasCitas(){
        return citaRepository.findAll();
    }


    //BUSCAR CITAS POR ID 
    public Optional<Cita> buscarCita(Integer id){
        return citaRepository.findById(id);
    }

    

}
