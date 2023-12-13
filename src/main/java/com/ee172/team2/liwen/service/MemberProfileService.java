package com.ee172.team2.liwen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MemberProfileService {

	@Autowired
	private MemberRepository memberDAO;

	public Member findById(Integer memberId) {
		Optional<Member> optional = memberDAO.findById(memberId);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Transactional
	public void deleteMemberProfile(Integer memberId) {
		memberDAO.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + memberId));
		memberDAO.deleteById(memberId);
	}

//	public void deleteById(Integer memberId) {
//		memberDAO.deleteById(memberId);
//	}

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
		if (memberProfile.getMemberPhoto() != null) {
			existMember.setMemberPhoto(memberProfile.getMemberPhoto());
		}
		if (memberProfile.getInsId() != null) {
			existMember.setInsId(memberProfile.getInsId());
		}

		return memberDAO.save(existMember);
	}

}
