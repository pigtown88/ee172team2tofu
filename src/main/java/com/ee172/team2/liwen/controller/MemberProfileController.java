package com.ee172.team2.liwen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ee172.team2.liwen.dto.ProfileDTO;
import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;
import com.ee172.team2.liwen.service.MemberProfileService;
import com.ee172.team2.steven.DTO.EmployeeDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/frontStage/member")
public class MemberProfileController {

	@Autowired
	private MemberProfileService profileService;

//	// 顯示單個會員的詳細資料
//	@GetMapping("/{memberId}")
//	public String showMemberProfile(@PathVariable Integer memberId, Model model) {
//		Member member = profileService.findById(memberId);
//		
//		model.addAttribute("member", member);
//		
//		return "liwen/member/showMember";
//	}
	
	// 顯示單個會員的詳細資料
    @GetMapping("/info")
    public ResponseEntity<ProfileDTO> showMemberProfile(HttpSession httpSession) {
    	SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) httpSession.getAttribute("loginMember");

        String memberEmail = memberDTO.getMemberEmail();

        // 取得註冊 newMember 的資料
        Member newMember = (Member) httpSession.getAttribute("newMember");
        
        System.out.println("從 HttpSession 中獲得的 newMember: " + newMember);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setMemberName(newMember.getMemberName());
        profileDTO.setMemberEmail(newMember.getMemberEmail());
        profileDTO.setMemberPwd(newMember.getMemberPwd());
        profileDTO.setMemberPhone(newMember.getMemberPhone());
        profileDTO.setMemberBirth(newMember.getMemberBirth());
        profileDTO.setMemberGender(newMember.getMemberGender());
        profileDTO.setMemberPhoto(newMember.getMemberPhoto());

        // 將 profileDTO 存儲在 session 中的 profile 屬性中
        httpSession.setAttribute("profile", profileDTO);

        return ResponseEntity.ok(profileDTO);
    }

	// 處理會員刪除
	@DeleteMapping("/{memberId}/delete")
    public ResponseEntity<?> deleteMemberProfile(@PathVariable Integer memberId) {
		profileService.deleteMemberProfile(memberId);
        return ResponseEntity.ok().build();
    }
	
//	@DeleteMapping("/{memberId}/delete")
//	public void deleteMemberProfile(@PathVariable Integer memberId) {
//		profileService.deleteById(memberId);
//	}
//	
//	@DeleteMapping("/{memberId}/delete")
//	public ResponseEntity<String> deleteMemberProfile(@PathVariable Integer memberId) {
//	    boolean deleted = profileService.deleteById(memberId);
//	    if (deleted) {
//	        return ResponseEntity.ok("删除成功");
//	    } else {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
//	    }
//	}
    
    // 更新會員資料
//	@ResponseBody
//    @PutMapping("/{memberId}/update")
//    public Member updateMemberProfile(@PathVariable Integer memberId, @RequestBody Member memberProfile) {
//    	return profileService.updateMemberProfile(memberId, memberProfile);
//    }

}
