package com.mediagenda.mediagenda.horario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mediagenda.mediagenda.horario.model.HorarioMedico;
import com.mediagenda.mediagenda.horario.service.HorarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @PostMapping("/{medicoId}")
    public ResponseEntity<HorarioMedico> guardarHorario(@PathVariable Long medicoId, @RequestBody HorarioMedico horario) {
        return ResponseEntity.ok(horarioService.guardarHorario(medicoId, horario));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<HorarioMedico>> obtenerHorarios(@PathVariable Long medicoId) {
        return ResponseEntity.ok(horarioService.obtenerHorariosPorMedico(medicoId));
    }
}