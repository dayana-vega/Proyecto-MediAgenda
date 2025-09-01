package com.mediagenda.mediagenda.cita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediagenda.mediagenda.cita.model.Cita;
import com.mediagenda.mediagenda.cita.service.CitaService;
import com.mediagenda.mediagenda.exceptions.CitaException;

@RestController
@RequestMapping("api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;

    //CREAR UNA CITA
    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Cita cita){
        try{
            Cita nuevaCita = citaService.crearCita(cita);
            return ResponseEntity.ok(nuevaCita);
        }catch (CitaException errorNuevaCita){
            return ResponseEntity.badRequest().body(errorNuevaCita.getMessage());
        }
    }

}
