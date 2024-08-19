package com.hackathon.ecocycle.global.image.domain.repository;

import com.hackathon.ecocycle.global.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
