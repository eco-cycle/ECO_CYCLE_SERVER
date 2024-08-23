package com.hackathon.ecocycle.domain.shoppingcart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponseDto {
    private List<CartProductResponseDto> cartProducts;
    private int totalPrice;
}
