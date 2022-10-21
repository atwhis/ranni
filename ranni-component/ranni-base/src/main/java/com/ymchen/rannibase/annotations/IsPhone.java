package com.ymchen.rannibase.annotations;

import com.ymchen.rannibase.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface IsPhone {
    String message() default "{phone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
