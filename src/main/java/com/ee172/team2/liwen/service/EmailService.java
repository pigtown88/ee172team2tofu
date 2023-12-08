package com.ee172.team2.liwen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender javaMailSender;
	
	public void sendConfirmationEmail(String from, String to, String subject, String verificationUrl, String linkText, Integer memberId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // 設定寄件者、收件者、主旨
        helper.setFrom(from); 	// "WebsightName<ee172test@gmail.com>"
        helper.setTo(to);
        helper.setSubject(subject);

     // 設定郵件內容，這裡使用HTML格式
        String htmlContent = "<p>請點擊以下連結啟用您的帳號: <a href='" + verificationUrl + "'>" + linkText + "</a></p>";
        htmlContent += "請於連結內輸入信箱驗證碼：" + memberId;
        
        helper.setText(htmlContent, true);

        // 寄送郵件
        javaMailSender.send(message);
    }
	
}
