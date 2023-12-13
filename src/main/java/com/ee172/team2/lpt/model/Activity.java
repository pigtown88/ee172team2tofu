package com.ee172.team2.lpt.model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ee172.team2.liwen.model.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "activity")
@Entity
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer activityId;

	private String activityName;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 轉換前端 String 日期到 Java 端日期格式
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp activityDayStart;

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 轉換前端 String 日期到 Java 端日期格式
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp activityDayEnd;

	private Integer activityPrice;

	private String activityType;

	private String activityIntro;

	@JsonBackReference("reserve-activity")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reserveId", referencedColumnName = "reserveId")
	private Reserve reserve;

	@JsonBackReference("member-activity")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId", referencedColumnName = "memberId")
	private Member member;

	@JsonIgnore
	@JsonManagedReference("activity-order")
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders;

}