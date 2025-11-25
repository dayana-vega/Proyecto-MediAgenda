package com.mediagenda.mediagenda.pago.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.repository.CitaRepository;
import com.mediagenda.mediagenda.enums.EstadoCita; // Asegúrate de tener este Enum
import com.mediagenda.mediagenda.exceptions.NotFoundException;
import com.mediagenda.mediagenda.pago.model.Pago;
import com.mediagenda.mediagenda.pago.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;

    @Transactional // Importante: si falla el pago, no se actualiza la cita
    public Pago procesarPago(Integer citaId, Pago pago) {
        // 1. Buscar la cita
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new NotFoundException("Cita no encontrada"));

        // 2. Asociar cita al pago
        pago.setCita(cita);
        pago.setMonto(25000.0); // Monto fijo simulado o dinámico según lógica

        // 3. Guardar pago
        Pago pagoGuardado = pagoRepository.save(pago);

        // 4. Actualizar estado de la cita automáticamente
        cita.setEstadoCita(EstadoCita.CONFIRMADA); // Asumiendo que tienes este estado
        citaRepository.save(cita);

        return pagoGuardado;
    }
}