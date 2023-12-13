package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.service.MemberService;
import com.ee172.team2.lpt.model.Activity;
import com.ee172.team2.lpt.service.ActivityService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping("/activitys")
	public List<Activity> showActivity() {
		List<Activity> allActivity = activityService.findActivity();
		return allActivity;
	}

	@GetMapping("/activitys/{id}")
	public ResponseEntity<Activity> getActivityById(@PathVariable Integer id) {

		try {
			Activity activity = activityService.findByActivityId(id);
			return ResponseEntity.ok(activity);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

//	@GetMapping("/activitys/{id}")
//    public ResponseEntity<?> getActivityById(@PathVariable Integer id) {
//        try {
//            Activity activity = activityService.findByActivityId(id);
//            return ResponseEntity.ok(activity);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activity not found");
//        }
//    }

	@PostMapping("/activity/{id}")
	public String postActivityDetails(@PathVariable("id") Integer activityId, Model model) {
		// 從資料庫中獲取活動詳細資料
		Activity activity = activityService.findByActivityId(activityId);
		model.addAttribute("activity", activity);
		return "details_activity";
	}

	@GetMapping("/getActivity/{id}")
	public ModelAndView getActivityDetails(@PathVariable("id") Integer activityId, Model model) {
		// 從資料庫中獲取活動詳細資料
		ModelAndView mav = new ModelAndView("patty/activity/details_activity");
		Activity activity = activityService.findByActivityId(activityId);
		model.addAttribute("activity", activity);
		return mav;
	}



}
