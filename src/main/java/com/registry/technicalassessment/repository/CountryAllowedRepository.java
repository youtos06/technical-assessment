package com.registry.technicalassessment.repository;

import com.registry.technicalassessment.model.CountryAllowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryAllowedRepository extends JpaRepository<CountryAllowed,Long> {
    CountryAllowed findByCountryCode(String code);
}
