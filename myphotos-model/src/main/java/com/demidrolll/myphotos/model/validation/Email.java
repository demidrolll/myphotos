package com.demidrolll.myphotos.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]")
@Constraint(validatedBy = {})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "{jakarta.validation.constraints.Email.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
