package com.registry.technicalassessment.annotation.contraint;

import com.registry.technicalassessment.annotation.validation.CountryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryConstraint {
    String message() default "Invalid country";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
