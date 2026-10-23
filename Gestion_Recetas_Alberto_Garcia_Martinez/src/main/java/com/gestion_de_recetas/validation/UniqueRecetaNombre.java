package com.gestion_de_recetas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueRecetaNombreValidator.class)
@Documented
public @interface UniqueRecetaNombre {

    String message() default "Ya existe una receta con ese nombre";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}