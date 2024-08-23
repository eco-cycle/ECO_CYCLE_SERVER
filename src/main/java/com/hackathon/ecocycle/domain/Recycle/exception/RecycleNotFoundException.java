package com.hackathon.ecocycle.domain.Recycle.exception;

import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RecycleNotFoundException extends Exception {
    private final ErrorCode errorCode;
}
