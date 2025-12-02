package com.mediagenda.mediagenda.usuario.model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.enums.RolUsuario;
import com.mediagenda.mediagenda.horario.model.HorarioMedico;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("MEDICO")
@Table(name = "Medicos")
@Getter
@Setter
public class Medico extends Usuario{

    private String especialidad;


    public Medico(){
        setRol(RolUsuario.MEDICO);

    }

    @ManyToMany(mappedBy = "medicos", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonBackReference
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioMedico> horarios;

}
