package com.hackathon.ecocycle.domain.member.domain.repository;

import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
}
