package com.mediagenda.mediagenda.cita.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.exceptions.CitaException;
import com.mediagenda.mediagenda.horario.model.HorarioMedico;
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
    public Cita crearCita (Cita cita) throws CitaException{
        HorarioMedico horarioMedico = horarioRepository.findByMedicoIdAndDia(
            cita.getMedico().getId(),
            cita.getFechaCita().getDayOfWeek()
        ).orElseThrow(() -> new CitaException("El médico no atiende ese día"));

        List<LocalTime> bloquesDisponibles = horarioService.generarBloques(horarioMedico);
        if (!bloquesDisponibles.contains(cita.getFechaCita().toLocalTime())){
            throw new CitaException("Ese horario no está en la agenda del médico");
        }

        if (citaRepository.findByMedicoIdAndFechaCita(
            cita.getMedico().getId(),   
            cita.getFechaCita()
            
        ).isPresent()){
            throw new CitaException ("El medico ya tiene una cita en ese horario");
        }
        return citaRepository.save(cita);
    }


    //TRAER TODO EL LISTADO DE CITAS
    public List<Cita> obtenerTodasCitas(){
        return citaRepository.findAll();
    }


    //BUSCAR CITAS POR ID 
    public Optional<Cita> buscarCita(Integer id){
        return citaRepository.findById(id);
    }

    //ACTUALIZAR CITA 
    public Cita actualizCita (Long Id, Cita citaActualizada){
        return citaRepository.findById(id).map(cita ->{
            cita.setFechaCita(citaActualizada.getFechaCita());
            cita.setPaciente(citaActualizada.getPaciente());
            cita.setMedico(citaActualizada.getMedico());

        }).orElseThrow(() -> new CitaException("Cita no encontrada con id: " + id));

    }

    //ELIMINAR CITA 
    public void eliminarCita (Integer id){
        citaRepository.deleteById(id);
    }
}
