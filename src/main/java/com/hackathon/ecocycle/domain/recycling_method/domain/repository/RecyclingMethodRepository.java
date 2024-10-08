package com.hackathon.ecocycle.domain.recycling_method.domain.repository;

import com.hackathon.ecocycle.domain.recycling_method.domain.entity.RecyclingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecyclingMethodRepository extends JpaRepository<RecyclingMethod, Long> {
    Optional<RecyclingMethod> findByCategory(String category);
}