package com.hackathon.ecocycle.domain.shoppingcart.application;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import com.hackathon.ecocycle.domain.product.domain.repository.ProductRepository;
import com.hackathon.ecocycle.domain.shoppingcart.domain.entity.Cart;
import com.hackathon.ecocycle.domain.shoppingcart.domain.entity.CartItem;
import com.hackathon.ecocycle.domain.shoppingcart.domain.repository.CartItemRepository;
import com.hackathon.ecocycle.domain.shoppingcart.domain.repository.CartRepository;
import com.hackathon.ecocycle.domain.shoppingcart.dto.request.PurchaseRequestDto;
import com.hackathon.ecocycle.domain.shoppingcart.dto.response.CartProductResponseDto;
import com.hackathon.ecocycle.domain.shoppingcart.dto.response.PurchaseResponseDto;
import com.hackathon.ecocycle.global.utils.GlobalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final GlobalUtil globalUtil;

    @Transactional(readOnly = true)
    public List<CartProductResponseDto> getAllCartProducts(String email) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        Cart cart = cartRepository.findByMemberId(member);

        if (cart == null) {
            throw new RuntimeException("장바구니 정보가 없습니다.");
        }

        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getPurchaseTime() == null)
                .map(cartItem -> new CartProductResponseDto(
                        cartItem.getProductId().getId(),
                        cartItem.getProductId().getName(),
                        cartItem.getProductId().getCategory(),
                        cartItem.getProductId().getSeller(),
                        cartItem.getProductId().getPrice(),
                        cartItem.getCartItemCount()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public CartProductResponseDto addCartItem(String email, Long productId, int count) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);

        Cart cart = cartRepository.findByMemberId(member);

        if (cart == null) {
            cartRepository.save(
                    Cart.builder()
                            .memberId(member)
                            .build()
            );
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart, product).orElseGet(
                () -> createNewCart(cart, product)
        );

        cartItem.addCartItemCount(count);

        return CartProductResponseDto.builder()
                .productId(cartItem.getProductId().getId())
                .name(cartItem.getProductId().getName())
                .category(cartItem.getProductId().getCategory())
                .seller(cartItem.getProductId().getSeller())
                .price(cartItem.getProductId().getPrice())
                .count(cartItem.getCartItemCount())
                .build();
    }

    @Transactional
    public Integer plusCountCartItem(String email, Long productId) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);

        Cart cart = cartRepository.findByMemberId(member);

        if (cart == null) {
            throw new RuntimeException("장바구니 정보가 없습니다.");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart, product).orElseThrow(() -> new RuntimeException("장바구니에 해당 제품이 없습니다."));

        cartItem.addCartItemCount(1);

        return cartItem.getCartItemCount();
    }

    @Transactional
    public int minusCountCartItem(String email, Long productId) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);

        Cart cart = cartRepository.findByMemberId(member);

        if (cart == null) {
            throw new RuntimeException("장바구니 정보가 없습니다.");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart, product).orElseThrow(() -> new RuntimeException("장바구니에 해당 제품이 없습니다."));;

        if (cartItem.getCartItemCount() == 1) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.addCartItemCount(-1);
        }

        return cartItem.getCartItemCount();
    }

    @Transactional
    public void deleteCartItem(String email, Long productId) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);

        Cart cart = cartRepository.findByMemberId(member);

        if (cart == null) {
            throw new RuntimeException("장바구니 정보가 없습니다.");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품 정보가 존재하지 않습니다."));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart, product).orElseThrow(() -> new RuntimeException("장바구니에 해당 제품이 없습니다."));
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public PurchaseResponseDto purchase(String email, PurchaseRequestDto purchaseRequestDto) throws MemberNotFoundException {
        Member member = globalUtil.findByMemberWithEmail(email);
        Cart cart = cartRepository.findByMemberId(member);

        if (cart == null) {
            throw new RuntimeException("장바구니 정보가 없습니다.");
        }

        List<CartProductResponseDto> cartProductResponseDtoList = new ArrayList<>();
        int totalPrice = 0;

        for (Long productId : purchaseRequestDto.getProductIdList()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
            CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart, product)
                    .orElseThrow(() -> new RuntimeException("상품이 장바구니에 담겨 있지 않습니다."));

            cartItem.addPurchaseTime();

            int itemTotalPrice = cartItem.getCartItemCount() * product.getPrice();
            totalPrice += itemTotalPrice;

            cartProductResponseDtoList.add(
                    CartProductResponseDto.builder()
                            .productId(cartItem.getProductId().getId())
                            .name(cartItem.getProductId().getName())
                            .category(cartItem.getProductId().getCategory())
                            .seller(cartItem.getProductId().getSeller())
                            .price(cartItem.getProductId().getPrice())
                            .count(cartItem.getCartItemCount())
                            .build()
            );
        }

        if (member.getPoint() > totalPrice) {
            member.calPoint(totalPrice);
        } else {
            throw new RuntimeException("포인트 부족으로 결제할 수 없습니다.");
        }

        return PurchaseResponseDto.builder()
                .cartProducts(cartProductResponseDtoList)
                .totalPrice(totalPrice)
                .build();
    }

    private CartItem createNewCart(Cart cart, Product product) {
        return cartItemRepository.save(
                CartItem.builder()
                        .cartId(cart)
                        .productId(product)
                        .build()
        );
    }
}
