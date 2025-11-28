package com.mediagenda.mediagenda.cita.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CrearCitaDTO {
    private LocalDateTime fechaCita;
    private Long medicoId;
    private Long pacienteId;
}