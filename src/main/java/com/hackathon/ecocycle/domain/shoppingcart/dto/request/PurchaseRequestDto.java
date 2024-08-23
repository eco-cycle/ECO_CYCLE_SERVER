package com.hackathon.ecocycle.domain.shoppingcart.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseRequestDto {
    private List<Long> productIdList;
}