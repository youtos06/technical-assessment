package com.registry.technicalassessment.annotation.validation;

import com.registry.technicalassessment.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GenderValidatorTest {
    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldBeValidGender(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setGender("M");
        userDto.setBirthDate(LocalDate.of(1997,7,14));

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotBeValidGender(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setGender("none");
        userDto.setBirthDate(LocalDate.of(1997,7,14));

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertEquals("Invalid user field -  gender",violations.iterator().next().getMessage());
    }
}