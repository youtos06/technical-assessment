package com.registry.technicalassessment.annotation.validation;


import com.registry.technicalassessment.annotation.contraint.PhoneConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {
    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
        // Init
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";
        return phoneNumber == null || phoneNumber.matches(patterns);
    }
}
