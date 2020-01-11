package com.clickbus.challenge.placesmanagement.repository;

import com.clickbus.challenge.placesmanagement.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
