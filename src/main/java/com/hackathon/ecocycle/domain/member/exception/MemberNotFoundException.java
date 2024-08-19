package com.hackathon.ecocycle.domain.member.exception;

import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MemberNotFoundException extends Exception {
    private final ErrorCode errorCode;
}
