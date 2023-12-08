package com.ee172.team2.liwen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "memberDating")
public class MemberDating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mDatingId;
	
	private Integer height;
	
	private Integer salary;
	
	@OneToOne
	@JoinColumn(name="memberId")
	private Member member;
	
	@OneToOne
	@JoinColumn(name="jobId")
	private Job jobId;
}
