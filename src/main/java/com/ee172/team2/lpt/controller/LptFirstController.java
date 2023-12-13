package com.ee172.team2.lpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ee172.team2.nemo.model.WeddingCouple;

@Controller
public class LptFirstController {

	@GetMapping("/arenaReserved")
	public String arenaReserved() {
		return "/lpt/reserve/arenaReserved";
	}

	@GetMapping("/addReserve")
	public String addReserve() {
		return "/lpt/reserve/addReserve";
	}

	
	@GetMapping("/addActivity/{reserveId}")
	public String addActivity(@PathVariable("reserveId") Integer reserveId, Model model) {
		model.addAttribute("reserveId", reserveId);
		return "/lpt/activity/addActivity";
	}
	
	@GetMapping("/activityHold/{activityId}")
	public String activityHold(@PathVariable("activityId") Integer activityId, Model model) {
		model.addAttribute("activityId", activityId);
		return "/lpt/activity/activityHold";
	}
	
	//nemo用跳轉
	
	@GetMapping("/addWedding/{reserveId}")
	public String addWedding(@PathVariable("reserveId") Integer reserveId, Model model) {
	    WeddingCouple weddingcouple = new WeddingCouple(); // 假设您有一个名为WeddingCouple的类
	    model.addAttribute("weddingcouple", weddingcouple);
//	    model.addAttribute("reserveId", reserveId);
	    return "nemo/weddingCoupleadd";
	}

	
//	@GetMapping("/addActivity/{reserveId}")
//	public ModelAndView addActivity(@PathVariable("reserveId") String reserveId, Model model) {
//		ModelAndView mav = new ModelAndView("/lpt/activity/addActivity");
//		model.addAttribute("reserveId", reserveId);
//		return mav;
//	}
	
	
	
}
