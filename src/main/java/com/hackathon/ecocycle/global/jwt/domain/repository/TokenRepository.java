package com.hackathon.ecocycle.global.jwt.domain.repository;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.hackathon.ecocycle.global.jwt.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByMember(Member member);

    Token findByRefreshToken(String refreshToken);
}
