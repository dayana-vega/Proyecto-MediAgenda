package com.mediagenda.mediagenda.usuario.model;

import com.mediagenda.mediagenda.enums.RolUsuario;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ADMIN")
@Table(name = "Administradores")
@Getter
@Setter
public class Administrador extends Usuario {

    public Administrador(){
        setRol(RolUsuario.ADMIN);
    }

}
