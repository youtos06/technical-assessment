package com.registry.technicalassessment.validator;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.repository.CountryAllowedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;

    @Mock
    private CountryAllowedRepository countryAllowedRepository;

    @Test
    void throwBusinessException_when_UserDto_Country_NotAllow_test(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("IT");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        when(countryAllowedRepository.findByCountryCode("IT")).thenReturn(null);
        Exception exception = assertThrows(BusinessApiException.class, () -> userValidator.validate(userDto) );
        assertEquals("Country not allowed : "+userDto.getCountry(),exception.getMessage());
    }
}