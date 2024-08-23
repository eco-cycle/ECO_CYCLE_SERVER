package com.hackathon.ecocycle.domain.shoppingcart.domain.entity;

import com.hackathon.ecocycle.domain.product.domain.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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
    @ColumnDefault("1")
    private int cartItemCount;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    public void addCartItemCount(int count) {
        this.cartItemCount += count;
    }

    public void addPurchaseTime() {
        this.purchaseTime = LocalDateTime.now();
    }
}
