package com.registry.technicalassessment.annotation.validation;

import com.registry.technicalassessment.annotation.contraint.GenderConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderConstraint,String> {
    @Override
    public void initialize(GenderConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender == null || "male".equals(gender) || "female".equals(gender);
    }
}
