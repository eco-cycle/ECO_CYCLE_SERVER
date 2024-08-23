package com.hackathon.ecocycle.domain.shoppingcart.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartProductRequestDto {
    private Long productId;
    private int count;
}
