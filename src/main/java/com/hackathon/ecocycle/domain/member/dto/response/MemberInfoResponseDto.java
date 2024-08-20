package com.hackathon.ecocycle.domain.member.dto.response;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponseDto(String name, String imageUrl, String location, String nickname) {
    public static MemberInfoResponseDto from(Member member) {
        return MemberInfoResponseDto.builder()
                .name(member.getName())
                .imageUrl(member.getImageUrl())
                .location(member.getLocation())
                .nickname(member.getNickname())
                .build();
    }
}
