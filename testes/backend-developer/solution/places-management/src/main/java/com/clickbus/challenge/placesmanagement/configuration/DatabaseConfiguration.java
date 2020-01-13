package com.clickbus.challenge.placesmanagement.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.clickbus.challenge.placesmanagement.repository"})
@EnableJpaAuditing
@EntityScan(basePackages = {"com.clickbus.challenge.placesmanagement.domain"})
@Configuration
public class DatabaseConfiguration {
}
