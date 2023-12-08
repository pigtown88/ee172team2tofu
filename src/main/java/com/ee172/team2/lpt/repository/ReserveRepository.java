package com.ee172.team2.lpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ee172.team2.lpt.model.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

   
	List<Reserve> findByMemberMemberId(Integer memberId);
	
}
