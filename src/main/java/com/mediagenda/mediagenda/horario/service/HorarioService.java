package com.mediagenda.mediagenda.horario.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mediagenda.mediagenda.horario.model.HorarioMedico;
import com.mediagenda.mediagenda.horario.repository.HorarioRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
@Validated
public class HorarioService {
    
    private final HorarioRepository horarioRepository;

    public List<LocalTime> generarBloques(HorarioMedico horarioMedico){
        List<LocalTime> bloques = new ArrayList<>();
        LocalTime actual = horarioMedico.getHorarioInicio();

        while (!actual.isAfter(horarioMedico.getHorarioFin().minusHours(1))){
            bloques.add(actual);
            actual = actual.plusHours(1);

        }
        return bloques;
    }
}
