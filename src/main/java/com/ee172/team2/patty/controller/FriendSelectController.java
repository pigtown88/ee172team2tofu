package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ee172.team2.liwen.model.MemberDating;
import com.ee172.team2.patty.service.FriendSelectService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendSelectController {

	@Autowired
	private FriendSelectService FriendSelectService;

//	@GetMapping("/friendselect")
//	public List<MemberDating> findByHeightSalaryAndJob(@RequestParam Integer minHeight, @RequestParam Integer maxHeight,
//			@RequestParam Integer minSalary, @RequestParam Integer maxSalary, @RequestParam String job) {
//		return FriendSelectService.findByHeightSalaryAndJob(minHeight, maxHeight, minSalary, maxSalary, job);
//	}

//	@GetMapping("/friend/search")
//	public ResponseEntity<List<MemberDating>> searchMembers(@RequestParam("minHeight") Integer minHeight, @RequestParam("maxHeight") Integer maxHeight, @RequestParam("minSalary") Integer minSalary, @RequestParam("maxSalary") Integer maxSalary, @RequestParam("jobName") String jobName, @RequestParam("gender") String gender) {
//		List<MemberDating> members = FriendSelectService.findMatchingMembers(minHeight, maxHeight, minSalary, maxSalary, jobName, gender);
//		return ResponseEntity.ok(members);
//	}

//	@GetMapping("/friendselect")
//	public List<MemberDating> searchMembers(@RequestParam Integer height, @RequestParam Integer salary,
//			@RequestParam String jobName) {
//		return FriendSelectService.findMembersByCriteria(height, salary, jobName);
//	}

	@GetMapping("/friendselect")
	public ResponseEntity<List<MemberDating>> findByHeightSalaryAndJob2(@RequestParam("minHeight") Integer minHeight,
			@RequestParam("minSalary") Integer minSalary, @RequestParam("jobName") String jobName,
			@RequestParam("gender") String gender) {
		List<MemberDating> meberDating = FriendSelectService.findByHeightSalaryAndJob(minHeight, minSalary, jobName,
				gender);
//        List<MemberDating> meberDating = FriendSelectService.findByHeightSalaryAndJob(minHeight,maxHeight, minSalary,maxSalary,jobName,gender);
		return ResponseEntity.ok(meberDating);
	}

}
