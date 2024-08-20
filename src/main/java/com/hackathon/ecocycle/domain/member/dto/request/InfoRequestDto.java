package com.hackathon.ecocycle.domain.member.dto.request;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record InfoRequestDto(
        @RequestPart(required = false) MultipartFile image,
        @RequestPart String nickname
        ) {
}