package com.ee172.team2.liwen.dto;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class SessionLoginMemberDTO {

	private Integer memberId;

	private String memberName;

	private String memberEmail;

	private String memberPwd;

	private Integer memberPhone;

	private Date memberBirth;

	private String memberGender;

//	private String memberAddress;

	private String memberPhoto;
	
	private String photoURL;

//	private Integer permission;
//
//	private boolean enabled;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date memberCt;
//
//	private Integer mInterestId;
}
