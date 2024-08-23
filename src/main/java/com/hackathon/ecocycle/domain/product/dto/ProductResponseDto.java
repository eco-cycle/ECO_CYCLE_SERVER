package com.hackathon.ecocycle.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "제품 조회")
@Builder
public class ProductResponseDto {
    private Long product_id;
    private String name;
    private String category;
    private String seller;
    private int price;
    private String titleImageUrl;
    private String descriptionImageUrl;
}
