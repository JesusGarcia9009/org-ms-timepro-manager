package org.ms.timepro.manager.anotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ms.timepro.manager.anotations.validator.RutValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RutValidator.class)
public @interface Rut {


    public String message() default "formato incorrecto RUT (e.j. 12345678-9)";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
