package com.mediagenda.mediagenda.cita.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mediagenda.mediagenda.enums.EstadoCita;
import com.mediagenda.mediagenda.pago.model.Pago;
import com.mediagenda.mediagenda.usuario.model.Medico;
import com.mediagenda.mediagenda.usuario.model.Paciente;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Paciente paciente;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id",referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Medico medico;

    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("cita") 
    private Pago pago;

    
    public Cita() {
    }

   
    public Cita(int id, LocalDateTime fechaCita, @NotNull EstadoCita estadoCita, Paciente paciente, Medico medico) {
        this.id = id;
        this.fechaCita = fechaCita;
        this.estadoCita = estadoCita;
        this.paciente = paciente;
        this.medico = medico;
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