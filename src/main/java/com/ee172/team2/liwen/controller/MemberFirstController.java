package com.ee172.team2.liwen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberFirstController {

	@GetMapping("/api/frontStage/member")
	public String memberCenter() {
		return "liwen/member/showMember";
	}
	
//	測試路徑
//	@GetMapping("/t")
//	public String test() {
//		return "liwen/member/index.html";
//	}
	
}
