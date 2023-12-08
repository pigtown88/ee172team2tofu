//package com.ee172.team2.liwen.controller;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ee172.team2.liwen.dto.SelectedInterestsDTO;
//import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
//import com.ee172.team2.liwen.model.Interest;
//import com.ee172.team2.liwen.model.Member;
//import com.ee172.team2.liwen.repository.InterestRepository;
//import com.ee172.team2.liwen.service.MemberService;
//
//import jakarta.servlet.http.HttpSession;
////import jakarta.validation.Valid;
//
//@Controller
//public class MemberController2 {
//
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private InterestRepository interestDAO;
//
//	@GetMapping("/member/register")
//	public String goRegisterPage() {
//		return "liwen/member/memberRegisterPage";
//	}
//
//	// 獲取所有興趣
//	@ModelAttribute("interests")
//	public List<Interest> getAllInterests() {
//		return interestDAO.findAll();
//	}
//
//	@PostMapping("/member/postRegister")
//	public String postRegisterForm(
//			@RequestParam("memberName") String memberName, 
////			@RequestParam("memberBirth") Date memberBirth,
//			@RequestParam("memberGender") String memberGender,
//			@RequestParam("memberEmail") String memberEmail,
//			@RequestParam("memberPwd") String memberPwd,
//			@RequestParam("memberPhone") Integer memberPhone,
////			@RequestParam("memberPhoto") MultipartFile memberPhoto,
//			@RequestParam("insId") List<Interest> insId,
//			HttpSession httpSession, 
//			Model model,
//			Member member) {
//
//		boolean isExist = memberService.checkMemberEmailIfExist(memberEmail);
//		
//		String resultMessage = memberService.registerMember(member);
//		model.addAttribute("resultMessage", resultMessage);
//
//		if (isExist) {
//			model.addAttribute("errorMsg", "已經有此帳號密碼");
//			if ("註冊成功".equals(resultMessage)) {
//				return "liwen/member/loginPage";
//			} 
//		} else {
//			Member newMember = new Member();
//			newMember.setMemberName(memberName);
////			newMember.setMemberBirth(memberBirth);
//			newMember.setMemberGender(memberGender);
//			newMember.setMemberEmail(memberEmail);
//			newMember.setMemberPwd(memberPwd);
//			newMember.setMemberPhone(memberPhone);
////			newMember.setMemberPhoto(photoBytes);
//			newMember.setInsId(insId);
//
//			memberService.addMember(member);
//			model.addAttribute("okMsg", "註冊成功");
//		}
//
//		return "liwen/member/memberRegisterPage"; 	//待修改：導向登入頁面
//	}
//	
//
//	@GetMapping("/member/login")
//	public String goLoginPage() {
//		return "liwen/member/loginPage";
//	}
//
//	@PostMapping("/member/login")
//	public String postLogin(
//			@RequestParam("memberEmail") String loginEmail, 
//			@RequestParam("memberPwd") String loginPwd,
//			HttpSession httpSession, 
//			Model model) {
//
//		Member result = memberService.checkLogin(loginEmail, loginPwd);
//
//		if (result != null) {
//			SessionLoginMemberDTO memberDTO = new SessionLoginMemberDTO();
//			memberDTO.setMemberId(result.getMemberId());
//			memberDTO.setMemberName(result.getMemberName());
//
//			httpSession.setAttribute("loginMember", memberDTO);
//			System.out.print(memberDTO);
//		} else {
//			model.addAttribute("loginFail", "帳號密碼錯誤");
//		}
//		return "public/home";
//
//	}
//
//	@GetMapping("/member/logout")
//	public String lougout(HttpSession httpSession) {
////		httpSession.invalidate();
//		System.out.println("登出 controller");
//		httpSession.removeAttribute("loginMember"); 	//remove loginMember 進行登出
//		return "redirect:/home";
//	}
//
//}
