package com.hackathon.ecocycle.domain.shoppingcart.domain.entity;

import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cartId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(name = "cart_item_count")
    private int cartItemCount;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @Builder
    public CartItem(Cart cartId, Product productId) {
        this.cartId = cartId;
        this.productId = productId;
        this.cartItemCount = 1;
        this.purchaseTime = null;
    }

    public void addCartItemCount(int count) {
        this.cartItemCount += count;
    }

    public void addPurchaseTime() {
        this.purchaseTime = LocalDateTime.now();
    }
}
