package com.registry.technicalassessment.annotation.validation;

import com.registry.technicalassessment.annotation.contraint.GenderConstraint;
import com.registry.technicalassessment.model.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class GenderValidator implements ConstraintValidator<GenderConstraint,String> {
    @Override
    public void initialize(GenderConstraint constraintAnnotation) {
        // Init
    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(gender) || Arrays.stream(Gender.values()).anyMatch(g -> g.name().equals(gender));
    }
}
