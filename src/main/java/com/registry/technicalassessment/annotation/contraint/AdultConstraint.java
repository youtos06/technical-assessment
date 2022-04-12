package com.registry.technicalassessment.annotation.contraint;

import com.registry.technicalassessment.annotation.validation.AdultValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultConstraint {
    String message() default "Invalid non adult age";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
