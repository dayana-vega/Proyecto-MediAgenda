package com.mediagenda.mediagenda.cita.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.cita.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>{ 

    Optional<Cita> findByPacienteRut (String Rut);

}
