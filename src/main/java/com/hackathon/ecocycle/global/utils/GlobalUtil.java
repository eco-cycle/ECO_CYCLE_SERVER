package com.hackathon.ecocycle.global.utils;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.domain.member.domain.repository.MemberRepository;
import com.hackathon.ecocycle.domain.member.exception.MemberNotFoundException;
import com.hackathon.ecocycle.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GlobalUtil {
    private final MemberRepository memberRepository;

    public Member findByMemberWithEmail(String email) throws MemberNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
