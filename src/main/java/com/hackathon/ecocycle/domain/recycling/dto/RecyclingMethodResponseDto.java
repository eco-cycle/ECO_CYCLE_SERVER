package com.hackathon.ecocycle.domain.recycling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RecyclingMethodResponseDto {
    private String category;
    private List<String> recyclingMethodImageUrlList;
}
