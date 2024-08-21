package com.hackathon.ecocycle.external.ML.dto;

import lombok.Builder;

@Builder
public record PredictedResponseDto(
        String predictedClass
) {
}
