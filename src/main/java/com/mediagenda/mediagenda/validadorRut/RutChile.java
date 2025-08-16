package com.mediagenda.mediagenda.validadorRut;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RutChileValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RutChile {
    String message() default "RUT inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
