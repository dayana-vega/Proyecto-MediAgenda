package com.mediagenda.mediagenda.horario.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.horario.model.HorarioMedico;

public interface HorarioRepository extends JpaRepository<HorarioMedico , Long>{

    List<HorarioMedico> findByMedicoId(Long medicoId);

    Optional<HorarioMedico> findByMedicoIdAndDia(Long medicoId, DayOfWeek dia);

}
