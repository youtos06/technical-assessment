package com.registry.technicalassessment.annotation.validation;

import com.registry.technicalassessment.annotation.contraint.AdultConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class AdultValidator implements ConstraintValidator<AdultConstraint, LocalDate> {
    @Override
    public void initialize(AdultConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(localDate)){
            return false;
        }
        LocalDate todayLocalDate = LocalDate.now();

        return Period.between(localDate,todayLocalDate).getYears() > 18 ;

    }
}
