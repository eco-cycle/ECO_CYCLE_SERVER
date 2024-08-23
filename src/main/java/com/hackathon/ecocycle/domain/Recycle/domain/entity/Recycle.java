package com.hackathon.ecocycle.domain.Recycle.domain.entity;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "recycle")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Recycle {
    @Id
    @Column(name = "recycle")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recycleId;

    private Long price;

    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String imageUrl;
}
