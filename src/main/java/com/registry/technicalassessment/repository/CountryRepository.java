package com.registry.technicalassessment.repository;

import com.registry.technicalassessment.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,String> {

}
