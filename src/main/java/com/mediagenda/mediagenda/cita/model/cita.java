package com.mediagenda.mediagenda.cita.model;

import java.time.LocalDateTime;

import com.mediagenda.mediagenda.enums.EstadoCita;
import com.mediagenda.mediagenda.usuario.model.Medico;
import com.mediagenda.mediagenda.usuario.model.Paciente;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime fechaCita;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;

    @ManyToOne
    @JoinColumn(name = "rut", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "rut", nullable = false)
    private Medico medico;

    public Cita() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }



    
}
