package com.registry.technicalassessment.annotation.validation;


import com.registry.technicalassessment.annotation.contraint.CountryConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {
    @Override
    public void initialize(CountryConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (country == null){
            return false;
        }
        country = country.toLowerCase();
        return country.equals("france") || country.equals("fr");
    }
}
