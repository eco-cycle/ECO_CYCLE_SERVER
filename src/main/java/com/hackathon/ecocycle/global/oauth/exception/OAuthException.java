package com.hackathon.ecocycle.global.oauth.exception;

import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OAuthException extends Exception {
    private final ErrorCode errorCode;
}
