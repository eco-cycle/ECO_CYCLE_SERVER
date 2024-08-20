package com.hackathon.ecocycle.external.lambda.dto;

import lombok.Builder;

@Builder
public record PredictedResponseDto(
        String predictedClass
) {
}
