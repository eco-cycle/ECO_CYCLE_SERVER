package com.hackathon.ecocycle.domain.member.domain.repository;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.hackathon.ecocycle.domain.member.domain.entity.QMember.member;

@Slf4j
@Repository
@AllArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne());
    }
}
