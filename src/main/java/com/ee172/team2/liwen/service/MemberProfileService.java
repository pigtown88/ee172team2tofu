package com.ee172.team2.liwen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;


@Service
public class MemberProfileService {

	@Autowired
	private MemberRepository memberDAO;

	
	   public Member updateMemberProfile(Integer memberId, Member memberProfile) {
		   Member existMember = memberDAO.findById(memberId)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "查無此會員"));


	        if (memberProfile.getMemberName() != null) {
	        	existMember.setMemberName(memberProfile.getMemberName());
	        }
	        if (memberProfile.getMemberEmail() != null) {
	        	existMember.setMemberEmail(memberProfile.getMemberEmail());
	        }
	        if (memberProfile.getMemberPwd() != null) {
	        	existMember.setMemberPwd(memberProfile.getMemberPwd());
	        }
	        if (memberProfile.getMemberPhone() != null) {
	        	existMember.setMemberPhone(memberProfile.getMemberPhone());
	        }
	        if (memberProfile.getMemberBirth() != null) {
	        	existMember.setMemberBirth(memberProfile.getMemberBirth());
	        }
	        if (memberProfile.getMemberPhoto() != null) {
	        	existMember.setMemberPhoto(memberProfile.getMemberPhoto());
	        }
	        if (memberProfile.getMemberPhoto() != null) {
	        	existMember.setInsId(memberProfile.getInsId());
	        }

	        return memberDAO.save(existMember);
	    }

}
