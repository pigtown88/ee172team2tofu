package com.ee172.team2.patty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;
import com.ee172.team2.patty.DTO.FriendReqDTO;
import com.ee172.team2.patty.model.FriendReq;
import com.ee172.team2.patty.repository.FriendReqRepository;
import com.ee172.team2.steven.DTO.EmployeeListDTO;
import com.ee172.team2.steven.model.Employee;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Service
public class FriendReqService {

	@Autowired
	private FriendReqRepository friendReqRepositoryDao;
	@Autowired
	private MemberRepository memberRepository;


//	
//	  public FriendReq friendReq(Integer receiverId,Integer senderId) {
//
//		    
//		    // 檢查好友關係是否已存在
//		    if (FriendReqRepositoryDao.existsBySenderIdAndReceiverId(session, receiverId)) {
//		      throw new RuntimeException("好友關係已存在！");
//		    }
//		    
//		    //新增好友
//		    FriendReq friendship = new FriendReq();
//		    friendship.setSenderId(session);
//		    friendship.setReceiverId(receiverId);
//		    friendship.setStatus(0);
//		   
//		    
//		    FriendReqRepositoryDao.save(friendship);
//	  }
	public boolean isFriendshipExists(Member sender, Member receiver) {
        return friendReqRepositoryDao.existsBySenderAndReceiver(sender, receiver);
    }
	
	
	public void addFriend(Integer senderId, Integer receiverId) {

		
		Member sender = memberRepository.findById(senderId).get();

		Member receiver = memberRepository.findById(receiverId).get();
		
		 


		// TODO Auto-generated method stub
		//new FriendReq 塞資料
		FriendReq friendship = new FriendReq();
		friendship.setSender(sender);
		friendship.setReceiver(receiver);
		friendship.setStatus(0);

		friendReqRepositoryDao.save(friendship);

	}
}
