package com.ee172.team2.liwen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ee172.team2.liwen.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByMemberId(Integer memberId);

	Member findByMemberEmail(String memberEmail);

	Optional<String> findEmailByMemberId(@Param("memberId") Integer memberId);

	@Modifying
	@Query("UPDATE Member m SET m.permission = :permission WHERE m.id = :memberId")
	void updatePermission(@Param("memberId") Integer memberId, @Param("permission") Integer permission);

}
