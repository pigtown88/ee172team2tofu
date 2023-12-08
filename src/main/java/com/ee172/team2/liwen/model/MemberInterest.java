package com.ee172.team2.liwen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "memberInterest")
public class MemberInterest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mInterestId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId",referencedColumnName = "memberId")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insId",referencedColumnName = "insId")
	private Interest interest; 
	
}
