package com.registry.technicalassessment.annotation.validation;

import com.registry.technicalassessment.dto.UserDto;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {
    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldBeValidPhone(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("france");
        userDto.setPhoneNumber("+212699887766");
        userDto.setBirthDate(LocalDate.of(1997,7,14));

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldNotBeValidPhone(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("fr");
        userDto.setPhoneNumber("+212 abc");
        userDto.setBirthDate(LocalDate.of(1997,7,14));

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertEquals(violations.iterator().next().getMessage(),
                "Invalid user field -  phone Number");
    }
}