package com.hackathon.ecocycle.domain.shoppingcart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartProductResponseDto {
    private Long productId;
    private String name;
    private String category;
    private String seller;
    private int price;
    private int count;
}