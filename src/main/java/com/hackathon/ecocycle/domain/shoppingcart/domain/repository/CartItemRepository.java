package com.hackathon.ecocycle.domain.shoppingcart.domain.repository;

import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import com.hackathon.ecocycle.domain.shoppingcart.domain.entity.Cart;
import com.hackathon.ecocycle.domain.shoppingcart.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(Cart cartId, Product productId);
}
