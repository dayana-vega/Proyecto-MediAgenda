package com.mediagenda.mediagenda.usuario.model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.enums.RolUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("MEDICO")
@Table(name = "Medicos")
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


    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    



}
