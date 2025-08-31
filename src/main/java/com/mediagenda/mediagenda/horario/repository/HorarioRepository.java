package com.mediagenda.mediagenda.horario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.horario.model.HorarioMedico;

public interface HorarioRepository extends JpaRepository<HorarioMedico , Long>{


}
