package com.mediagenda.mediagenda.pago.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mediagenda.mediagenda.pago.model.Pago;
import com.mediagenda.mediagenda.pago.service.PagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping("/pagar/{citaId}")
    public ResponseEntity<?> realizarPago(@PathVariable Integer citaId, @RequestBody Pago pago) {
        try {
            Pago nuevoPago = pagoService.procesarPago(citaId, pago);
            return ResponseEntity.ok(nuevoPago);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el pago: " + e.getMessage());
        }
    }
}