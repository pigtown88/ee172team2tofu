package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.patty.DTO.showFriendReqDTO;
import com.ee172.team2.patty.service.FriendService;
import com.ee172.team2.steven.model.Employee;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShowFriendReq {
	
	@Autowired
	private FriendService friendService;

	@GetMapping("/showFriendReq")
	@ResponseBody
	public ResponseEntity<List<showFriendReqDTO>> showFriendReq(HttpSession session) {
//		SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
//		if (memberDTO == null) {
//			return null;
//		}
//		Integer yourId = memberDTO.getMemberId();
        List<showFriendReqDTO> Friends = friendService.showFriendReqById(1);
        System.out.println(Friends);
        return ResponseEntity.ok(Friends);
		
	}
}
