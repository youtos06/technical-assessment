package com.registry.technicalassessment.dto;


import com.registry.technicalassessment.annotation.contraint.AdultConstraint;
import com.registry.technicalassessment.annotation.contraint.CountryConstraint;
import com.registry.technicalassessment.annotation.contraint.GenderConstraint;
import com.registry.technicalassessment.annotation.contraint.PhoneConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Birth date is mandatory")
    @AdultConstraint
    private LocalDate birthDate;

    @NotNull(message = "Country is mandatory")
    @CountryConstraint
    private String country;

    @GenderConstraint
    private String gender;

    @PhoneConstraint
    private String phoneNumber;
}
