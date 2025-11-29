package com.mediagenda.mediagenda.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediagenda.mediagenda.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByRut(String rut);
    List<Usuario> findByRol(com.mediagenda.mediagenda.enums.RolUsuario rol);
    


}
