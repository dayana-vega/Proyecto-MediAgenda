package com.mediagenda.mediagenda.validadorRut;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutChileValidator implements ConstraintValidator<RutChile, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String rut = value.trim().toUpperCase();

        if (!rut.matches("^[0-9]{1,8}-[0-9K]$")) {
            return false;
        }

        String[] parts = rut.split("-");
        String cuerpo = parts[0];
        char dvIngresado = parts[1].charAt(0);

        return dvIngresado == calcularDV(cuerpo);
    }

    private char calcularDV(String cuerpo) {
        int suma = 0;
        int factor = 2;

        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * factor;
            factor = (factor == 7) ? 2 : factor + 1;
        }

        int resto = 11 - (suma % 11);
        if (resto == 11) return '0';
        if (resto == 10) return 'K';
        return (char) ('0' + resto);
    }
}
