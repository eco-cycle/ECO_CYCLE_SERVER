package com.hackathon.ecocycle.domain.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "seller")
    private String seller;

    @Column(name = "price")
    private int price;

    @Column(name = "title_image_url")
    private String titleImageUrl;

    @Column(name = "description_image_url")
    private String descriptionImageUrl;

    public void addTitleImageUrl(String imageUrl) {
        this.titleImageUrl = imageUrl;
    }
}