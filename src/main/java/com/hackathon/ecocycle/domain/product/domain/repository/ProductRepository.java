package com.hackathon.ecocycle.domain.product.domain.repository;

import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
