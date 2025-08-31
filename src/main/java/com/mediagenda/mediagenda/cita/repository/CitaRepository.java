package com.mediagenda.mediagenda.cita.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.cita.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer>{ 

    Optional<Cita> findByPacienteRut (String Rut);

    Optional<Cita> findByMedicoIdAndFechaCita ( Long medico_id, LocalDateTime fechaCita );

}
