package com.hackathon.ecocycle.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewMemberResponseDto {
    private boolean isNew;

    @Builder
    public NewMemberResponseDto(boolean isNew) {
        this.isNew = isNew;
    }
}
