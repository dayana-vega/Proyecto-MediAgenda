package com.mediagenda.mediagenda.cita.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.usuario.model.Paciente;

public interface CitaRepository extends JpaRepository<Cita, Integer>{ 

    Optional<Cita> findByPacienteRut (Paciente Rut);

}
