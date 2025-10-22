package com.mediagenda.mediagenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad
public class SecurityConfig {

    // Define el Bean que configura la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // 1. Deshabilitar la protección CSRF (Cross-Site Request Forgery)
        // Es necesario deshabilitarla para aplicaciones REST sin gestión de sesión.
        http.csrf(csrf -> csrf.disable()) 
            
            // 2. Configurar las reglas de autorización
            // Permite el acceso a CUALQUIER solicitud sin autenticación.
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); 
        
        // Construye y devuelve la cadena de filtros configurada
        return http.build();
    }
}