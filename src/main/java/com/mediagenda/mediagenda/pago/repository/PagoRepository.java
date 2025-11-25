package com.mediagenda.mediagenda.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mediagenda.mediagenda.pago.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}