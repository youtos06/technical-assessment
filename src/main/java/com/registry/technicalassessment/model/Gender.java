package com.registry.technicalassessment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public enum Gender {
    M("MALE"),
    F("FEMALE");

    private String label;

    Gender(String label) {
        this.label = label;
    }
}
