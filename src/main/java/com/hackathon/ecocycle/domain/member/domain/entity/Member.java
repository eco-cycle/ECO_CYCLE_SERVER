package com.hackathon.ecocycle.domain.member.domain.entity;

import com.hackathon.ecocycle.global.jwt.domain.entity.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "member")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "location")
    private String location;

    @Column(name = "social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Token token;

    public void updateLocation(String location) {
        this.location = location;
    }

    public void updateProfileInfo(String imageUrl, String nickname) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }
}
