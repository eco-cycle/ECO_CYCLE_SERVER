package com.hackathon.ecocycle.domain.Recycle.domain.repository;

import com.hackathon.ecocycle.domain.Recycle.domain.entity.Recycle;
import com.hackathon.ecocycle.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecycleRepository extends JpaRepository<Recycle, Long> {
    List<Recycle> findAllByMember(Member member);

    List<Recycle> findAllByMemberNot(Member member);
}
