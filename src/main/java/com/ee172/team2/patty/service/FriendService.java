package com.ee172.team2.patty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;
import com.ee172.team2.patty.DTO.showFriendReqDTO;
import com.ee172.team2.patty.model.Friend;
import com.ee172.team2.patty.model.FriendReq;
import com.ee172.team2.patty.repository.FriendReqRepository;

@Service
public class FriendService {

	@Autowired
	private FriendReqRepository friendReqDao;
	
	@Autowired
	private MemberRepository memberRepository;
	

	

	
	public void addFriend() {
		
//		Member member1 = friendReqRepositoryDao.findById()
//
//		Member member2 = friendReqRepositoryDao.findById(memberId2).get();
//		
//		Friend friendcomfirm = new Friend();
//		
//		friendcomfirm.setMemberId1(member1);
//		
//		friendcomfirm.setMemberId2(member2);
//		friendship.setStatus(2);
//		friendReqRepositoryDao.save(friendship);
	}
	
//	public void addFriend() {
//		friendReq.setStatus(1);
//		friendReqRepositoryDao.save(friendReq.setStatus(1));
//	}
	
	public void rejectFriend() {
		FriendReq friendship = new FriendReq();
		friendship.setStatus(0);
		friendReqDao.save(friendship);
	}
	
	public void confirmFriend (Integer memberId1,Integer memberId2) {
		
		Member member1 = memberRepository.findById(memberId1).get();

		Member member2 = memberRepository.findById(memberId2).get();
		
		Friend friendcomfirm = new Friend();
		
		friendcomfirm.setMemberId1(member1);
		
		friendcomfirm.setMemberId2(member2);
		
	}
	
	//DTO轉換
	private showFriendReqDTO convertoshowFriendReqDTO(FriendReq friendReq) {
		showFriendReqDTO dto = new showFriendReqDTO();
		dto.setReceiverId(friendReqDao.findByReceiver(friendReq.getReceiver()));
		dto.setSenderId(friendReqDao.findBySender(friendReq.getSender()));
		
		
//		dto.setMemberName(friendReq.getMember().getMemberName());
		
		return dto;
	}
	
//    public List<showFriendReqDTO> showFriendReqById(Integer yourId){
//        
//    	List<FriendReq> friendReq = friendReqDao.findByMember_memberId(yourId);
//    	
//    	List<showFriendReqDTO> showFriendReqDTOList = new ArrayList<>();
//
//        for (FriendReq friendReq1 : friendReq) {
//            showFriendReqDTOList.add(showFriendReqById(friendReq1));
//        }
//
//        return showFriendReqDTOList;
//    }
	
	public List<showFriendReqDTO> showFriendReqById(Integer yourId) {
	    List<FriendReq> friendReqList = friendReqDao.findByTheSenderId(yourId);
	    System.out.println(friendReqList);
	    List<showFriendReqDTO> showFriendReqDTOList = new ArrayList<>();

	    for (FriendReq friendReq : friendReqList) {
	        showFriendReqDTO dto = new showFriendReqDTO();
	        // Assuming there are getters and setters in showFriendReqDTO
//	        dto.setFriendRequestId(friendReq.getFriendRequestId());
//	        dto.setSenderName(friendReq.getSender().getName());
	        
	        dto.setReceiverId(friendReqDao.findByReceiver(friendReq.getReceiver()));
			dto.setSenderId(friendReqDao.findBySender(friendReq.getSender()));
//			dto.setMemberName(friendReq.getMember().getMemberName());
	        
	        // Set other properties accordingly
	        
	        showFriendReqDTOList.add(dto);
	    }

	    return showFriendReqDTOList;
	}




    }
	
	  



