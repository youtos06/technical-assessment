package com.registry.technicalassessment.annotation.contraint;


import com.registry.technicalassessment.annotation.validation.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneConstraint {
    String message() default "Invalid user field -  phone Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
