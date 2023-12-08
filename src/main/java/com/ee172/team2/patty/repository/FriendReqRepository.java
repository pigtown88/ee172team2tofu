package com.ee172.team2.patty.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.patty.model.FriendReq;

public interface FriendReqRepository extends JpaRepository<FriendReq, Integer> {
	

	
//	boolean existsBySenderIdAndReceiverId(HttpSession session, Integer receiverId);

//	boolean existsBySenderIdAndReceiverId(Integer senderId,Integer receiverId);
	
	boolean existsBySenderAndReceiver(Member sender, Member receiver);

	Integer findByReceiver(Member receiver);

	Integer findBySender(Member sender);
	
	@Query( value = "SELECT * FROM friendReq WHERE senderId = ?1",nativeQuery = true)
	List<FriendReq> findByTheSenderId(Integer myId);


}
