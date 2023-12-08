package com.ee172.team2.liwen.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "job")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobId;

	private String jobName;

	@JsonManagedReference("Job-memberDating")
	@OneToMany(mappedBy = "jobId", cascade = CascadeType.ALL)
	private Set<MemberDating> memberDating;

}
