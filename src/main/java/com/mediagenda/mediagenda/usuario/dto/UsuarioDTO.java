package com.mediagenda.mediagenda.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediagenda.mediagenda.enums.RolUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private RolUsuario rol;    

}
