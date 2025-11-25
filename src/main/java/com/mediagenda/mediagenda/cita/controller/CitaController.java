package com.mediagenda.mediagenda.cita.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.service.CitaService;
import com.mediagenda.mediagenda.exceptions.CitaException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Cita cita){
        try{
            return ResponseEntity.ok(citaService.crearCita(cita));
        }catch (CitaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(citaService.obtenerTodasCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCita(@PathVariable Integer id) {
        return citaService.buscarCita(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable Integer id, @RequestBody Cita cita) {
        try {
            return ResponseEntity.ok(citaService.actualizarCita(id, cita));
        } catch (CitaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable Integer id) {
        try {
            citaService.eliminarCita(id);
            return ResponseEntity.ok("Cita eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}