package com.hackathon.ecocycle.domain.product.application;

import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import com.hackathon.ecocycle.domain.product.domain.repository.ProductRepository;
import com.hackathon.ecocycle.domain.product.dto.ProductResponseDto;
import com.hackathon.ecocycle.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

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

    @Transactional
    public void productAddImage(Long productId, MultipartFile image) throws IOException {
        String newImageUrl = imageService.uploadImage(image);
        log.info("New image uploaded: {}", newImageUrl);

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품 정보가 존재하지 않습니다."));
        product.addTitleImageUrl(newImageUrl);
    }
}
