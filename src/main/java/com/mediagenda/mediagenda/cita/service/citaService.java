package com.mediagenda.mediagenda.cita.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mediagenda.mediagenda.cita.DTO.CrearCitaDTO;
import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.enums.EstadoCita;
import com.mediagenda.mediagenda.exceptions.CitaException;
import com.mediagenda.mediagenda.exceptions.NotFoundException;
import com.mediagenda.mediagenda.horario.model.HorarioMedico;
import com.mediagenda.mediagenda.horario.repository.HorarioRepository;
import com.mediagenda.mediagenda.horario.service.HorarioService;
import com.mediagenda.mediagenda.usuario.model.Medico;
import com.mediagenda.mediagenda.usuario.model.Paciente;
import com.mediagenda.mediagenda.usuario.model.Usuario;
import com.mediagenda.mediagenda.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class CitaService {

    private final HorarioService horarioService;
    private final HorarioRepository horarioRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;

    private void validarDisponibilidad(Long medicoId, java.time.LocalDateTime fechaCita) throws CitaException {
        HorarioMedico horarioMedico = horarioRepository.findByMedicoIdAndDia(
            medicoId,
            fechaCita.getDayOfWeek()
        ).orElseThrow(() -> new CitaException("El médico no atiende ese día (" + fechaCita.getDayOfWeek() + ")"));

        List<LocalTime> bloquesDisponibles = horarioService.generarBloques(horarioMedico);
        if (!bloquesDisponibles.contains(fechaCita.toLocalTime())){
            throw new CitaException("Ese horario no está en la agenda del médico");
        }

        if (citaRepository.findByMedicoIdAndFechaCita(medicoId, fechaCita).isPresent()){
            throw new CitaException("El medico ya tiene una cita ocupada en ese horario");
        }
    }

    public Cita crearCita(Cita cita) throws CitaException{
        validarDisponibilidad(cita.getMedico().getId(), cita.getFechaCita());
        return citaRepository.save(cita);
    }

    public Cita crearCitaDesdeDTO(CrearCitaDTO dto) throws CitaException {
            Usuario usuarioMedico = usuarioRepository.findById(dto.getMedicoId())
                    .orElseThrow(() -> new NotFoundException("Id de Médico no encontrado"));

            if (!(usuarioMedico instanceof Medico)) {
                throw new CitaException("El ID ingresado corresponde a un usuario que NO es médico");
            }
            Medico medico = (Medico) usuarioMedico;

            Usuario usuarioPaciente = usuarioRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Id de Paciente no encontrado"));

            if (!(usuarioPaciente instanceof Paciente)) {
                throw new CitaException("El ID ingresado corresponde a un usuario que NO es paciente");
            }
            Paciente paciente = (Paciente) usuarioPaciente; 

            Cita cita = new Cita();
            cita.setFechaCita(dto.getFechaCita());
            cita.setEstadoCita(EstadoCita.PENDIENTE);
            cita.setMedico(medico);
            cita.setPaciente(paciente);

            return crearCita(cita);
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

        boolean fechaCambio = !citaExistente.getFechaCita().isEqual(citaActualizada.getFechaCita());
        
        if (fechaCambio) {
            validarDisponibilidad(citaExistente.getMedico().getId(), citaActualizada.getFechaCita());
            citaExistente.setFechaCita(citaActualizada.getFechaCita());
        }

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
    public List<LocalTime> obtenerHorasOcupadas(Long medicoId, LocalDate fecha) {
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);

        List<Cita> citas = citaRepository.findByMedicoIdAndFechaCitaBetween(medicoId, inicioDia, finDia);

        return citas.stream()
                .filter(c -> c.getEstadoCita() != EstadoCita.CANCELADA) // Si está cancelada, el horario cuenta como libre
                .map(c -> c.getFechaCita().toLocalTime())
                .toList();
    }

    // En CitaService.java
    public List<Cita> listarCitasPorUsuario(Long usuarioId, com.mediagenda.mediagenda.enums.RolUsuario rol) {
        if (rol == com.mediagenda.mediagenda.enums.RolUsuario.PACIENTE) {
            return citaRepository.findByPacienteId(usuarioId);
        } else {
            return citaRepository.findByMedicoId(usuarioId);
        }
    }
    



}