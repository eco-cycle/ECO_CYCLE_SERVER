package com.hackathon.ecocycle.domain.product.api;

import com.hackathon.ecocycle.domain.product.application.ProductService;
import com.hackathon.ecocycle.domain.product.dto.ProductResponseDto;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "전체 제품 조회", description = "제품 목록 출력입니다.")
    @GetMapping("/product/all")
    public ResponseTemplate<List<ProductResponseDto>> getAllProducts() {
        return new ResponseTemplate<>(HttpStatus.OK, "제품 목록 조회 성공", productService.getAllProducts());
    }

    @GetMapping("/product/{productId}")
    public ResponseTemplate<ProductResponseDto> getProductById(@PathVariable Long productId) {
        return new ResponseTemplate<>(HttpStatus.OK, "제품 단일 조회 성공", productService.getProductById(productId));
    }

    @Operation(summary = "상품 이미지 등록")
    @PostMapping("/product/image/{productId}")
    public ResponseTemplate<?> productAddImage(@PathVariable Long productId, @RequestParam("file") MultipartFile file) throws IOException {
        productService.productAddImage(productId, file);
        return new ResponseTemplate<>(HttpStatus.OK, "상품 사진 추가");
    }
}
