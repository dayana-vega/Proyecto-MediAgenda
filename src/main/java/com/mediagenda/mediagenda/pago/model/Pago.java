package com.mediagenda.mediagenda.pago.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.enums.TipoPago;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private TipoPago metodoPago; 

    @OneToOne
    @JoinColumn(name = "cita_id", referencedColumnName = "id")
    @JsonIgnore
    private Cita cita;

    @PrePersist
    public void prePersist() {
        this.fechaPago = LocalDateTime.now();
    }
}