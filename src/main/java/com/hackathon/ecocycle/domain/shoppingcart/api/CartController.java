package com.hackathon.ecocycle.domain.shoppingcart.api;

import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.domain.shoppingcart.application.CartService;
import com.hackathon.ecocycle.domain.shoppingcart.dto.request.CartProductRequestDto;
import com.hackathon.ecocycle.domain.shoppingcart.dto.request.PurchaseRequestDto;
import com.hackathon.ecocycle.domain.shoppingcart.dto.response.CartProductResponseDto;
import com.hackathon.ecocycle.domain.shoppingcart.dto.response.PurchaseResponseDto;
import com.hackathon.ecocycle.global.exception.dto.ErrorResponse;
import com.hackathon.ecocycle.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Shopping Cart Controller", description = "Shopping Cart API")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니 제품 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 제품 조회 성공", content = @Content(schema = @Schema(implementation = CartProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/cart")
    public ResponseTemplate<List<CartProductResponseDto>> getAllCartItem(@AuthenticationPrincipal String email) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "장바구니 조회 성공", cartService.getAllCartProducts(email));
    }

    @Operation(summary = "장바구니에 제품 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니에 제품 추가 성공", content = @Content(schema = @Schema(implementation = CartProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/cart")
    public ResponseTemplate<CartProductResponseDto> addCartItem(@AuthenticationPrincipal String email, @RequestBody CartProductRequestDto cartProductRequestDto) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.CREATED, "장바구니에 제품 추가 성공", cartService.addCartItem(email, cartProductRequestDto.getProductId(), cartProductRequestDto.getCount()));
    }

    @Operation(summary = "장바구니 수량 증가", description = "장바구니에 담긴 제품의 수량 증가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니에 제품 수량 증가 성공", content = @Content(schema = @Schema(implementation = CartProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/cart/plus/{productId}")
    public ResponseTemplate<Integer> plusCountCartItem(@AuthenticationPrincipal String email, @PathVariable Long productId) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "장바구니 제품 수량 증가 성공", cartService.plusCountCartItem(email, productId));
    }

    @Operation(summary = "장바구니 수량 감소", description = "장바구니에 담긴 제품의 수량 감소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 제품 수량 감소 성공", content = @Content(schema = @Schema(implementation = CartProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/cart/minus/{productId}")
    public ResponseTemplate<Integer> minusCountCartItem(@AuthenticationPrincipal String email, @PathVariable Long productId) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "장바구니 제품 수량 감소 성공", cartService.minusCountCartItem(email, productId));
    }

    @Operation(summary = "장바구니 제품 삭제", description = "장바구니에 담긴 제품 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 제품 삭제 성공", content = @Content(schema = @Schema(implementation = CartProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/cart/{productId}")
    public ResponseTemplate<?> deleteCartItem(@AuthenticationPrincipal String email, @PathVariable Long productId) throws MemberNotFoundException {
        cartService.deleteCartItem(email, productId);
        return new ResponseTemplate<>(HttpStatus.OK, "장바구니에서 제품 삭제 성공");
    }

    @Operation(summary = "제품 결제", description = "장바구니에 담긴 제품 결제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 결제 성공", content = @Content(schema = @Schema(implementation = CartProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/purchase")
    public ResponseTemplate<PurchaseResponseDto> purchase(@AuthenticationPrincipal String email, @RequestBody PurchaseRequestDto purchaseRequestDto) throws MemberNotFoundException {
        return new ResponseTemplate<>(HttpStatus.OK, "제품 결제 성공", cartService.purchase(email, purchaseRequestDto));
    }
}
