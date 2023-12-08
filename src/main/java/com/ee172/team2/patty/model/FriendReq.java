package com.ee172.team2.patty.model;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.steven.model.Department;
import com.ee172.team2.steven.model.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

@Data
@Entity
@Table(name = "friendReq")
public class FriendReq {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendReqId")
	private Integer friendReqId;

	@ManyToOne
	@JoinColumn(name = "senderId", referencedColumnName = "memberId")
	private Member sender;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "receiverId", referencedColumnName = "memberId")
	private Member receiver;

	@Column(name = "status")
	private Integer status;
	
//	@JsonBackReference("friendReq-member")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
//    private Member member;

}
