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

    @Transactional
    public Pago procesarPago(Integer citaId, Pago pago) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new NotFoundException("Cita no encontrada"));

        pago.setCita(cita);

        Pago pagoGuardado = pagoRepository.save(pago);

        cita.setEstadoCita(EstadoCita.CONFIRMADA);
        citaRepository.save(cita);

        return pagoGuardado;
    }
}