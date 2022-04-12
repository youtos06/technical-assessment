package com.registry.technicalassessment.annotation.contraint;

import com.registry.technicalassessment.annotation.validation.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderConstraint {
    String message() default "Invalid user field -  gender";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
