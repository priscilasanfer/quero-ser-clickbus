package com.clickbus.challenge.placesmanagement.repository;

import com.clickbus.challenge.placesmanagement.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
