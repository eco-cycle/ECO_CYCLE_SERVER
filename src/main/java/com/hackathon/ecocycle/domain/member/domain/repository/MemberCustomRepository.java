package com.hackathon.ecocycle.domain.member.domain.repository;


import com.hackathon.ecocycle.domain.member.domain.entity.Member;

import java.util.Optional;

public interface MemberCustomRepository {
    Optional<Member> findByEmail(String email);
}

