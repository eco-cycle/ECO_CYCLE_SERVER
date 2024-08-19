package com.hackathon.ecocycle.domain.auth.exception;

import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class InvalidTokenException extends Throwable {
    private final ErrorCode errorCode;
}
