package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.lpt.model.Activity;
import com.ee172.team2.lpt.service.ActivityService;

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
        Activity activity = activityService.findByActivityId(id);

        if (activity != null) {
            return ResponseEntity.ok(activity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
