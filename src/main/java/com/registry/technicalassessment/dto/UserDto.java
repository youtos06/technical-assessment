package com.registry.technicalassessment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private LocalDate birthDate;
    private String country;
    private String phoneNumber;
    private String gender;
}
