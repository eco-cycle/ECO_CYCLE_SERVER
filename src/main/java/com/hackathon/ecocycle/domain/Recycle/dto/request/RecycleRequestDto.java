package com.hackathon.ecocycle.domain.Recycle.dto.request;

public record RecycleRequestDto(
        String title,
        String location,
        Long price
) {
}
