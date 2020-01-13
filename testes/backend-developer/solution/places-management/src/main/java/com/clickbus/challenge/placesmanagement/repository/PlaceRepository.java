package com.clickbus.challenge.placesmanagement.repository;

import com.clickbus.challenge.placesmanagement.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByNameContainingIgnoreCase(String name);
}
