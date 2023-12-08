package com.ee172.team2.liwen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.service.MemberProfileService;

@RestController
@RequestMapping("/api/member")
public class MemberProfileController {

	@Autowired
	private MemberProfileService memProfileService;

	// 更新會員資料
	@PutMapping("/{memberId}")
	public Member updateMemberProfile(@PathVariable Integer memberId, @RequestBody Member memberProfile) {
		return memProfileService.updateMemberProfile(memberId, memberProfile);
	}

}
