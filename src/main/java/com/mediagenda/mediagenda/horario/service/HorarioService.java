package com.mediagenda.mediagenda.horario.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mediagenda.mediagenda.exceptions.NotFoundException; // Asegúrate de tener esta excepción o usa RuntimeException
import com.mediagenda.mediagenda.horario.model.HorarioMedico;
import com.mediagenda.mediagenda.horario.repository.HorarioRepository;
import com.mediagenda.mediagenda.usuario.model.Medico;
import com.mediagenda.mediagenda.usuario.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Validated
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final MedicoRepository medicoRepository;

    // Generar bloques (Tu método original, lo mantenemos igual)
    public List<LocalTime> generarBloques(HorarioMedico horarioMedico){
        List<LocalTime> bloques = new ArrayList<>();
        LocalTime actual = horarioMedico.getHorarioInicio();
        while (!actual.isAfter(horarioMedico.getHorarioFin().minusHours(1))){
            bloques.add(actual);
            actual = actual.plusHours(1);
        }
        return bloques;
    }

    // NUEVO: Crear o Actualizar Horario
    public HorarioMedico guardarHorario(Long medicoId, HorarioMedico horario) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new NotFoundException("Médico no encontrado"));

        // Verificar si ya existe horario para ese día
        Optional<HorarioMedico> existente = horarioRepository.findByMedicoIdAndDia(medicoId, horario.getDia());
        
        if(existente.isPresent()) {
            HorarioMedico h = existente.get();
            h.setHorarioInicio(horario.getHorarioInicio());
            h.setHorarioFin(horario.getHorarioFin());
            return horarioRepository.save(h);
        } else {
            horario.setMedico(medico);
            return horarioRepository.save(horario);
        }
    }

    // NUEVO: Obtener horarios del médico
    public List<HorarioMedico> obtenerHorariosPorMedico(Long medicoId) {
        return horarioRepository.findByMedicoId(medicoId);
    }
}