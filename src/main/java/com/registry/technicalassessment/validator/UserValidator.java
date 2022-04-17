package com.registry.technicalassessment.validator;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.model.CountryAllowed;
import com.registry.technicalassessment.repository.CountryAllowedRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@AllArgsConstructor
public class UserValidator{

    private final CountryAllowedRepository countryAllowedRepository;

    /**
     * Check if country in userDto is part of allowed countries
     * @throws BusinessApiException with Bad Request status if country not allowed
     * @param userDto user dto to validate
     */
    public void validate(UserDto userDto){
        CountryAllowed countryAllowed =  countryAllowedRepository.findByCountry_code(userDto.getCountry());

        if (Objects.isNull(countryAllowed)){
            throw new BusinessApiException("Country not allowed : " + userDto.getCountry(), HttpStatus.BAD_REQUEST);
        }

    }
}
