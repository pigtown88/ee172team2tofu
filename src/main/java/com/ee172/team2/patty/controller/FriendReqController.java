package com.ee172.team2.patty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.patty.service.FriendReqService;
import com.ee172.team2.patty.service.FriendService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/friendship")
public class FriendReqController {

	@Autowired
	private FriendReqService friendReqService;
	
	@Autowired
	private FriendService friendService;

	@PostMapping("/{receiverId}")
	public ResponseEntity<?> addFriends(HttpSession session, @PathVariable String receiverId) {
	    SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
	    if (memberDTO == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
	    try {
	        // 在這裡使用 memberId 進行業務邏輯
	        friendReqService.addFriend(memberDTO.getMemberId(),Integer.valueOf(receiverId));
	        return ResponseEntity.ok("好友請求發送成功！");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("好友請求發送失败：" + e.getMessage());
	    }
	}
	
	@PostMapping("/confirm/{member2Id}")
	public ResponseEntity<?> confirmFriends(HttpSession session,@PathVariable String member2Id){
		SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
	    
	    try {
	    	
	    	friendService.addFriend();
	    	friendService.confirmFriend(memberDTO.getMemberId(), Integer.valueOf(member2Id));
	    	return ResponseEntity.ok("好友添加成功！");
	    	
	    }catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("好友添加失敗：" + e.getMessage());
	    }
	}
	    
	    @PostMapping("/reject/{member2Id}")
		public ResponseEntity<?> rejectFriends(HttpSession session,@PathVariable String member2Id){		    
		    try {
		    	friendService.rejectFriend();
		    	return ResponseEntity.ok("好友拒絕成功！");
		    	
		    }catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("好友拒絕失敗：" + e.getMessage());
		    }
		
	}
	


}
