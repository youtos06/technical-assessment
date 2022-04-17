package com.registry.technicalassessment.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CountryAllowed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private Country country;
}
