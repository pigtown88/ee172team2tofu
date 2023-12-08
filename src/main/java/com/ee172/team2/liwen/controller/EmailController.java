//package com.ee172.team2.liwen.controller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ee172.team2.liwen.repository.MemberRepository;
//import com.ee172.team2.liwen.resource.EmailMessage;
//import com.ee172.team2.liwen.service.EmailService;
//import com.ee172.team2.liwen.service.MemberService;
//
//@RestController
//public class EmailController {
//
//	@Autowired
//	private EmailService emailService;
//	
//	@Autowired
//	private MemberRepository memberDAO;
//	
//	
////	@GetMapping("/memberId/{memberId}")
////    public ResponseEntity<String> sendConfirmationEmail(@PathVariable Integer memberId) {
////        // 在這裡，您可以根據 memberId 找到對應的會員郵件地址
////        // 假設 memberService 是處理會員相關邏輯的服務類
////		Optional<String> toEmail = memberDAO.findEmailByMemberId(memberId);
////
////		if (toEmail.isPresent()) {
////            emailService.sendConfirmationEmail(toEmail.get(), memberId);
////            return ResponseEntity.ok("成功寄送確認信");  // 200
////        } else {
////            return ResponseEntity.badRequest().body("未找到對應的會員郵件地址");  // 400
////        }
////    }
//	
//	
////	// PostMan測試成功
////	@PostMapping("/member/sendEmail")
////	public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) {
////		emailService.sendConfirmationEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getText());
////        return ResponseEntity.ok("Success");	//200
////    }
//
//}
