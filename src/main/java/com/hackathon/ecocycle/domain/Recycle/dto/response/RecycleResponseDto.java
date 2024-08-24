package com.hackathon.ecocycle.domain.Recycle.dto.response;

import com.hackathon.ecocycle.domain.Recycle.domain.entity.Recycle;
import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RecycleResponseDto(
        Long id,
        String location,
        String type,
        Long price,
        LocalDateTime createdAt,
        String imageUrl,
        String nickname,
        String userImage
) {
    public static RecycleResponseDto from(Recycle recycle, Member member) {
        return RecycleResponseDto.builder()
                .id(recycle.getRecycleId())
                .location(recycle.getLocation())
                .type(recycle.getType())
                .location(recycle.getLocation())
                .price(recycle.getPrice())
                .createdAt(recycle.getCreatedAt())
                .imageUrl(recycle.getImageUrl())
                .nickname(member.getNickname())
                .userImage(member.getImageUrl())
                .build();
    }
}
