package com.mediagenda.mediagenda.cita.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.exceptions.CitaException;
import com.mediagenda.mediagenda.horario.model.HorarioMedico;
import com.mediagenda.mediagenda.horario.repository.HorarioRepository;
import com.mediagenda.mediagenda.horario.service.HorarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Usamos Lombok para inyección más limpia
public class CitaService {

    private final HorarioService horarioService;
    private final HorarioRepository horarioRepository;
    private final CitaRepository citaRepository;

    // Método auxiliar para validar disponibilidad (Reutilizable)
    private void validarDisponibilidad(Long medicoId, java.time.LocalDateTime fechaCita) throws CitaException {
        // 1. Verificar si el médico trabaja ese día
        HorarioMedico horarioMedico = horarioRepository.findByMedicoIdAndDia(
            medicoId,
            fechaCita.getDayOfWeek()
        ).orElseThrow(() -> new CitaException("El médico no atiende ese día (" + fechaCita.getDayOfWeek() + ")"));

        // 2. Verificar si la hora está dentro de sus bloques
        List<LocalTime> bloquesDisponibles = horarioService.generarBloques(horarioMedico);
        if (!bloquesDisponibles.contains(fechaCita.toLocalTime())){
            throw new CitaException("Ese horario no está en la agenda del médico");
        }

        // 3. Verificar si ya hay otra cita a esa misma hora
        if (citaRepository.findByMedicoIdAndFechaCita(medicoId, fechaCita).isPresent()){
            throw new CitaException("El medico ya tiene una cita ocupada en ese horario");
        }
    }

    public Cita crearCita(Cita cita) throws CitaException{
        validarDisponibilidad(cita.getMedico().getId(), cita.getFechaCita());
        return citaRepository.save(cita);
    }

    public List<Cita> obtenerTodasCitas(){
        return citaRepository.findAll();
    }

    public Optional<Cita> buscarCita(Integer id){
        return citaRepository.findById(id);
    }

    public Cita actualizarCita(Integer id, Cita citaActualizada) throws CitaException {
        Cita citaExistente = citaRepository.findById(id)
            .orElseThrow(() -> new CitaException("Cita no encontrada con id: " + id));

        // IMPORTANTE: Si se cambia la fecha, hay que validar disponibilidad de nuevo
        boolean fechaCambio = !citaExistente.getFechaCita().isEqual(citaActualizada.getFechaCita());
        
        if (fechaCambio) {
            validarDisponibilidad(citaExistente.getMedico().getId(), citaActualizada.getFechaCita());
            citaExistente.setFechaCita(citaActualizada.getFechaCita());
        }

        // Permitimos cambiar estado (ej: CANCELADA, REALIZADA)
        if(citaActualizada.getEstadoCita() != null) {
            citaExistente.setEstadoCita(citaActualizada.getEstadoCita());
        }

        return citaRepository.save(citaExistente);
    }

    public void eliminarCita(Integer id){
        if(!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }
}