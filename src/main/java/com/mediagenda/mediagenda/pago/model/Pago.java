package com.mediagenda.mediagenda.pago.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediagenda.mediagenda.cita.model.Cita;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;
    private LocalDateTime fechaPago;
    private String metodoPago; // "TARJETA_CREDITO", "DEBITO", "EFECTIVO"

    // Relación 1 a 1 con Cita
    @OneToOne
    @JoinColumn(name = "cita_id", referencedColumnName = "id")
    @JsonIgnore // Evitar loop infinito al serializar
    private Cita cita;

    @PrePersist
    public void prePersist() {
        this.fechaPago = LocalDateTime.now();
    }
}