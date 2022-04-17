package com.registry.technicalassessment.repository;

import com.registry.technicalassessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findUserByName(String name);
    List<User> findUserByNameAndBirthDateAndCountry_code(String name, LocalDate birthDate,String code);
    Optional<User> findById(Long aLong);
}
