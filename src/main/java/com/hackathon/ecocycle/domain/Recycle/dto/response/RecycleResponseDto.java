package com.hackathon.ecocycle.domain.Recycle.dto.response;

import com.hackathon.ecocycle.domain.Recycle.domain.entity.Recycle;
import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import lombok.Builder;

@Builder
public record RecycleResponseDto(
        Long id,
        Long price,
        String type,
        String imageUrl,
        String nickname,
        String location) {
    public static RecycleResponseDto from(Recycle recycle, Member member) {
        return RecycleResponseDto.builder()
                .id(recycle.getRecycleId())
                .price(recycle.getPrice())
                .type(recycle.getType())
                .imageUrl(recycle.getImageUrl())
                .nickname(member.getNickname())
                .location(member.getLocation())
                .build();
    }
}
