package com.mediagenda.mediagenda.cita.model;

import java.time.LocalDateTime;

import com.mediagenda.mediagenda.enums.EstadoCita;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;


@Entity
public class cita {
    @Id
    private int id;
    private LocalDateTime fechaCita;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;

    public cita() {
    }

    public cita(int id, LocalDateTime fechaCita, @NotNull EstadoCita estadoCita) {
        this.id = id;
        this.fechaCita = fechaCita;
        this.estadoCita = estadoCita;
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

    


    
}
