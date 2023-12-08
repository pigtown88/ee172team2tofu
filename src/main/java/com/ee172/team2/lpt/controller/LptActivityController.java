
package com.ee172.team2.lpt.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.lpt.DTO.ActivityDTO;
import com.ee172.team2.lpt.service.ActivityService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/activity")
public class LptActivityController {

	@Autowired
	private ActivityService activityService;

	//獲得此場地所有活動
	@GetMapping("/getAllActivityByReserveId")
	public ResponseEntity<List<ActivityDTO>> getAllActivityByReserveId(@RequestParam String reserveId) {
		List<ActivityDTO> activityDTOs = activityService.findActivityByReserveId(Integer.valueOf(reserveId));
		return ResponseEntity.ok(activityDTOs);
	}

	//舉辦活動
	@PostMapping("/addActivity")
	public ResponseEntity<?> addActivity(HttpSession session,@RequestBody ActivityDTO addActivityDTO) {
		SessionLoginMemberDTO hostDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
		ActivityDTO activityDTO = addActivityDTO;
		activityDTO.setHostId(String.valueOf(hostDTO.getMemberId()));
		
		try {
			activityService.addActivity(activityDTO);
			return ResponseEntity.ok("成功舉辦活動");
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body("活動舉辦失敗");
		}
	}
	
	//取消活動
	@DeleteMapping("/deleteActivity/{activityId}")
	public ResponseEntity<?> deleteActivity(@PathVariable Integer activityId){
		activityService.deleteByActivityId(activityId);
		return ResponseEntity.ok().build();
	}
	
	
	
}

