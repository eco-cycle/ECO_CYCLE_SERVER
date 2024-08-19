package com.hackathon.ecocycle.global.image.exception;

import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ImageNotFoundException extends Exception {
    private final ErrorCode errorCode;
}
