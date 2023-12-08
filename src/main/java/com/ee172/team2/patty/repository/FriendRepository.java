package com.ee172.team2.patty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.patty.model.Friend;
import com.ee172.team2.patty.model.FriendReq;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
	
	

	

}
