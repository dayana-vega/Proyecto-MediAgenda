package com.mediagenda.mediagenda.usuario.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.enums.RolUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;

@Entity
@DiscriminatorValue("PACIENTE")
@Table(name = "Pacientes")
public class Paciente extends Usuario {

    @Past
    private LocalDate fechaNacimiento;


    public Paciente(){
        setRol(RolUsuario.PACIENTE);
        
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medico_paciente",
        joinColumns = @JoinColumn(name = "paciente_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "medico_id", referencedColumnName = "id")
    )
    @JsonIgnore
    @JsonManagedReference
    private Set<Medico> medicos = new HashSet<>();

    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;

    

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(Set<Medico> medicos) {
        this.medicos = medicos;
    }

    




}
