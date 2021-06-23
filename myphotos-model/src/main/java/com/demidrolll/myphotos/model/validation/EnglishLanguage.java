package com.demidrolll.myphotos.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnglishLanguageConstraintValidator.class})
public @interface EnglishLanguage {

    String message() default "javax.validation.constraints.EnglishLanguage.message";

    boolean withNumbers() default true;

    boolean withPunctuations() default true;

    boolean withSpecialSymbols() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
