package com.hackathon.ecocycle.domain.shoppingcart.domain.entity;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @OneToMany(mappedBy = "cartId")
    List<CartItem> cartItems = new ArrayList<>();
}
