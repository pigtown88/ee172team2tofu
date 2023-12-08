package com.ee172.team2.lpt.model;

import java.util.Date;
import java.util.List;

import com.ee172.team2.liwen.model.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "reserve")
@Entity
public class Reserve {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reserveId;

	private Date reserveDayStart;

	private Date reserveDayEnd;

	@JsonBackReference("arena-reserve")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arenaId", referencedColumnName = "arenaId")
	private Arena arena;

	@JsonBackReference("member-reserve")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId", referencedColumnName = "memberId")
	private Member member;

	@JsonManagedReference("reserve-activity")
	@OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Activity> activities;

}