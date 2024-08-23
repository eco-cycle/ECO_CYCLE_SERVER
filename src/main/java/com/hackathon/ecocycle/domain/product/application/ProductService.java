package com.hackathon.ecocycle.domain.product.application;

import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import com.hackathon.ecocycle.domain.product.domain.repository.ProductRepository;
import com.hackathon.ecocycle.domain.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream().map(product -> ProductResponseDto.builder()
                .product_id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .seller(product.getSeller())
                .price(product.getPrice())
                .titleImageUrl(product.getTitleImageUrl())
                .descriptionImageUrl(product.getDescriptionImageUrl())
                .build()).collect(Collectors.toList());
    }
}
