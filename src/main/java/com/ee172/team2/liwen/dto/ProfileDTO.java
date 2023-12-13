package com.ee172.team2.liwen.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
	
	private Integer memberId;

	private String memberName;

	private String memberEmail;

	private String memberPwd;

	private Integer memberPhone;
	
	private Date memberBirth;

	private String memberGender;

	private byte[] memberPhoto;
	
}
