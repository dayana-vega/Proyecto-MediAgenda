package com.mediagenda.mediagenda.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.usuario.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

}
