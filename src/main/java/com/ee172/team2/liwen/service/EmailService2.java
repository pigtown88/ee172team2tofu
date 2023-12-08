//package com.ee172.team2.liwen.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService2 {
//
//	@Autowired
//    private JavaMailSender javaMailSender;
//	
//	public void sendConfirmationEmail(String toEmail, Integer memberId) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("會員帳號啟用確認信");
//        message.setText("請點擊以下連結啟用您的帳號：localhost:8087/ee172/certification/memberId=" + memberId);
//
//        javaMailSender.send(message);
//    }
//	
//
////	public void sendConfirmationEmail(String toEmail, Integer memberId) {
//////        // 生成確認碼（這裡可以使用您的邏輯生成）
////        String confirmationCode = generateConfirmationCode();
////
////        // 創建確認連結
////        String confirmationLink = "localhost:8087/ee172/certification/memberId=" + memberId + "&confirmationCode=" + confirmationCode;
////
////        // 創建郵件訊息
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setTo(toEmail);
////        message.setSubject("會員帳號啟用確認信");
////        message.setText("請點擊以下連結啟用您的帳號：" + confirmationLink);
////
////        // 發送郵件
////        javaMailSender.send(message);
////    }
////
////    private String generateConfirmationCode() {
////        // 實作生成確認碼的邏輯，可以使用UUID或其他方法
////        // 返回生成的確認碼
////    }
//	
//}
