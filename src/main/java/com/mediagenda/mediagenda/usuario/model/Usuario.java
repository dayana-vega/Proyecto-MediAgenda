package com.mediagenda.mediagenda.usuario.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mediagenda.mediagenda.enums.RolUsuario;
import com.mediagenda.mediagenda.validadorRut.RutChile;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Tipo_Usuario")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // Usamos un nombre de propiedad (que existe en el JSON)
    include = JsonTypeInfo.As.EXISTING_PROPERTY, // Indica que la propiedad ya existe en el JSON
    property = "rol" // La propiedad que Jackson debe inspeccionar
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Paciente.class, name = "PACIENTE"), // Si rol es "PACIENTE", usa la clase Paciente
    @JsonSubTypes.Type(value = Medico.class, name = "MEDICO")      // Si rol es "MEDICO", usa la clase Medico
})
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(length = 10, unique = true)
    @NotBlank(message = "El rut es obligatorio")
    @RutChile(message = "Ingresa un rut valido en formato: 12345678-5 o 12345678-K")
    private String rut;

    @NotBlank
    @Size(max = 60)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ' -]+$", message = "El nombre solo deben contener letras")
    private String nombre;
    
    @NotBlank
    @Size(max = 60)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ' -]+$", message = "El apellido solo deben contener letras")
    private String apellido;

    @Email
    @NotBlank
    @Size(max = 254)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
    @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
      message = "La contraseña debe incluir mayúsculas, minúsculas y números")
    @Column(nullable = false)
    private String contrasenia;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    protected Usuario(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    

    


}
