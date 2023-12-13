package com.ee172.team2.liwen.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.ee172.team2.liwen.model.Interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDTO {
	
//	private Integer memberId;

	private String memberName;

	private String memberEmail;

	private String memberPwd;

	private Integer memberPhone;
	
	private Date memberBirth;

	private String memberGender;

	private byte[] memberPhoto;
	
	private Interest insId;

	
}
