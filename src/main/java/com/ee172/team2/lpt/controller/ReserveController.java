package com.ee172.team2.lpt.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.lpt.DTO.ReserveDTO;
import com.ee172.team2.lpt.service.ReserveService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

	@Autowired
	private ReserveService reserveService;

	// 獲得登入會員所有場地預約
	@GetMapping("/getAllReserve")
	public ResponseEntity<List<ReserveDTO>> getAllReserveByMemberId(HttpSession session) {
		SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
		
		if(memberDTO != null) {
			List<ReserveDTO> reserveDTOs = reserveService.findReserveByMemberId(memberDTO.getMemberId());
			return ResponseEntity.ok(reserveDTOs);
		}
		return ResponseEntity.ok(null);
	}

	//獲得登入會員所有場地預約，測試
	@GetMapping("/getAllReserve/{id}")
	public ResponseEntity<List<ReserveDTO>> getAllReserveByMemberId(@PathVariable Integer id) {
		List<ReserveDTO> reserveDTOs = reserveService.findReserveByMemberId(id);
		return ResponseEntity.ok(reserveDTOs);
	}

	// 預約場地，之後要修改成前端傳輸資料形式
	@PostMapping("/addReserve")
	public ResponseEntity<?> addReserve(HttpSession session, @RequestParam String reserveDayStart,
			@RequestParam String reserveDayEnd, @RequestParam String arenaId) {
		SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
		ReserveDTO reserveDTO = new ReserveDTO();
		reserveDTO.setMemberId(String.valueOf(memberDTO.getMemberId()));
		reserveDTO.setReserveDayStart(reserveDayStart);
		reserveDTO.setReserveDayEnd(reserveDayEnd);
		reserveDTO.setArenaId(arenaId);

		try {
			reserveService.addReserve(reserveDTO);
			return ResponseEntity.ok("成功預約場地");
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body("預約失敗");
		}
	}
	
//	//postman測試
//	@PostMapping("/addReserve")
//	public ResponseEntity<?> addReserve(@RequestParam String memberid, @RequestParam String reserveDayStart,
//			@RequestParam String reserveDayEnd, @RequestParam String arenaId) {
//		ReserveDTO reserveDTO = new ReserveDTO();
//		reserveDTO.setMemberId(memberid);
//		reserveDTO.setReserveDayStart(reserveDayStart);
//		reserveDTO.setReserveDayEnd(reserveDayEnd);
//		reserveDTO.setArenaId(arenaId);
//
//		try {
//			reserveService.addReserve(reserveDTO);
//			return ResponseEntity.ok("成功預約場地");
//		} catch (ParseException e) {
//			return ResponseEntity.badRequest().body("預約失敗");
//		}
//	}
	
	
}
