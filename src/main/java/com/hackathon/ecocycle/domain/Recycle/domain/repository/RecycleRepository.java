package com.hackathon.ecocycle.domain.Recycle.domain.repository;

import com.hackathon.ecocycle.domain.Recycle.domain.entity.Recycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecycleRepository extends JpaRepository<Recycle, Long> {
}
