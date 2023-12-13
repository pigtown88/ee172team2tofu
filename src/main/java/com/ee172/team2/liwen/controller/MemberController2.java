//package com.ee172.team2.liwen.controller;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ee172.team2.liwen.dto.MemberRegisterDTO;
//import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
//import com.ee172.team2.liwen.model.Interest;
//import com.ee172.team2.liwen.model.Member;
//import com.ee172.team2.liwen.repository.InterestRepository;
//import com.ee172.team2.liwen.repository.MemberRepository;
//import com.ee172.team2.liwen.service.EmailService;
//import com.ee172.team2.liwen.service.MemberService;
//
//import jakarta.mail.MessagingException;
//import jakarta.servlet.http.HttpSession;
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
//	@Autowired
//	private EmailService emailService;
//	
//	@Autowired
//	private MemberRepository memberDAO;
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
////	// 可以用
////	@PostMapping("/member/postRegister")
////	public String postRegisterForm(
////			@RequestParam("memberName") String memberName, 
////			@RequestParam("memberBirth")  String memberBirth,
////			@RequestParam("memberGender") String memberGender,
////			@RequestParam("memberEmail") String memberEmail,
////			@RequestParam("memberPwd") String memberPwd,
////			@RequestParam("memberPhone") Integer memberPhone,
////			@RequestParam("photo") MultipartFile photo,
////			@RequestParam("insId") Interest insId,
////			HttpSession httpSession, 
////			Model model,
////			@ModelAttribute Member member) throws IOException, ParseException, MessagingException {
////		
////		boolean isExist = memberService.checkMemberEmailIfExist(memberEmail);
////		
////		String resultMessage = memberService.registerMember(member);
////		model.addAttribute("resultMessage", resultMessage);
////
////		if (isExist) {
////			model.addAttribute("errorMsg", "已經有此帳號密碼");
////		}else {
////			Member newMember = new Member();
////			newMember.setMemberName(memberName);
////			newMember.setMemberGender(memberGender);
////			newMember.setMemberEmail(memberEmail);
////			newMember.setMemberPwd(memberPwd);
////			newMember.setMemberPhone(memberPhone);
////			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
////			Date memberBirthDate = inputFormat.parse(memberBirth);
////			newMember.setMemberBirth(memberBirthDate);
////			newMember.setMemberPhoto(photo.getBytes());
////			newMember.setInsId(insId);
////			
////			// 調用 addMemberAndGenerateToken 方法，同時處理資料庫存儲和生成驗證 token
////            String verificationToken = memberService.addMemberAndGenerateToken(newMember);
////
////           // 構建驗證郵件相關資訊
////            model.addAttribute("token", verificationToken);
////            
////            Integer memberId = memberDAO.save(newMember).getMemberId();
////            
////            String verificationUrl = "http://localhost:8087/ee172/member/verify";
////            String emailSubject = "會員帳號啟用確認信";
////            String linkText = "點擊這裡";
////            
////            // 發送驗證郵件
////            emailService.sendConfirmationEmail("ee172test@gmail.com", newMember.getMemberEmail(), emailSubject, verificationUrl, linkText, memberId);
////            
////            model.addAttribute("okMsg", "註冊成功，請檢查您的郵件進行驗證");
////			
////		}
////		return "redirect:/member/register"; 	//導向 GET 方法中的 /member/register
////	}
//	
//	@PostMapping("/member/postRegister")
//	public String postRegisterForm(@ModelAttribute MemberRegisterDTO memberRegisterDTO,
//	                               @RequestParam("photo") MultipartFile photo,
//	                               HttpSession httpSession, 
//	                               Model model) throws IOException, ParseException, MessagingException {
//
//	    boolean isExist = memberService.checkMemberEmailIfExist(memberRegisterDTO.getMemberEmail());
//	    String resultMessage = memberService.registerMember(memberRegisterDTO, photo);
//	    model.addAttribute("resultMessage", resultMessage);
//
//	    if (isExist) {
//	        model.addAttribute("errorMsg", "已經有此帳號密碼");
//	    } else {
//	        // 調用 addMemberAndGenerateToken 方法，同時處理資料庫存儲和生成驗證 token
//	        String verificationToken = memberService.addMemberAndGenerateToken(memberRegisterDTO);
//
//	        // 構建驗證郵件相關資訊
//	        model.addAttribute("token", verificationToken);
//
//	        Integer memberId = memberDAO.save(memberRegisterDTO).getMemberId();
//
//	        String verificationUrl = "http://localhost:8087/ee172/member/verify";
//	        String emailSubject = "會員帳號啟用確認信";
//	        String linkText = "點擊這裡";
//
//	        // 發送驗證郵件
//	        emailService.sendConfirmationEmail("ee172test@gmail.com", memberRegisterDTO.getMemberEmail(), emailSubject, verificationUrl, linkText, memberId);
//
//	        model.addAttribute("okMsg", "註冊成功，請檢查您的郵件進行驗證");
//	    }
//	    return "redirect:/member/register"; // 導向 GET 方法中的 /member/register
//	}
//	
//	@GetMapping("/member/verify")
//	public String verifyMember() {
//		return "liwen/member/verification";
//	}
//	
//	@PostMapping("/member/verify")
//	public String verifyMember(@RequestParam("token") Integer verificationToken, Model model) {
//		// 直接使用會員ID作為驗證 token
//		Member member = memberDAO.findByMemberId(verificationToken);
//		Integer memberId = member.getMemberId();
//	    
//	    if ( memberId != verificationToken){
//	        // 處理轉換失敗的情況，這裡可以返回相應的錯誤頁面或訊息
//	    	model.addAttribute("errorMsg", "驗證碼無效");
//	        return "liwen/member/verification";
//	    }
//
//	    // 調用 MemberService 的方法來更新權限
//	    memberService.updatePermission(memberId, 1);
//
//	    // 返回一個驗證成功的頁面，同時將相應的屬性添加到Model中
//	    model.addAttribute("verificationSuccess", true);
//	    model.addAttribute("memberId", memberId);  
//
//	    // 返回一個成功頁面或其他相應的處理
//	    return "redirect:/liwen/member/verificationSuccessPage";
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
//			// 將圖片轉換為 Base64 字串
//	        String base64Image = Base64.getEncoder().encodeToString(result.getMemberPhoto());
//			
//			SessionLoginMemberDTO memberDTO = new SessionLoginMemberDTO();
//			memberDTO.setMemberId(result.getMemberId());
//			memberDTO.setMemberName(result.getMemberName());
//			memberDTO.setMemberPhoto(base64Image);
//
//			httpSession.setAttribute("loginMember", memberDTO);
//			System.out.print(memberDTO);
//			
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
