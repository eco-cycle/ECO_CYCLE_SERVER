package com.hackathon.ecocycle.domain.product.api;

import com.hackathon.ecocycle.domain.product.application.ProductService;
import com.hackathon.ecocycle.domain.product.dto.ProductResponseDto;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
