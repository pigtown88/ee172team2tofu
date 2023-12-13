package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.lpt.model.Order;
import com.ee172.team2.lpt.service.OrderService;
import com.ee172.team2.patty.DTO.OrderDTO;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/123/{id}")
	public ResponseEntity<?> addOrder(HttpSession session,@PathVariable Integer id){
		SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
	    if (memberDTO == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
	    try {
	        // 在這裡使用 memberId 進行業務邏輯
	    	orderService.addOrder(memberDTO.getMemberId(),Integer.valueOf(id));
	        return ResponseEntity.ok("訂購成功");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("訂購失败：" + e.getMessage());
	    }
	}
	
	@GetMapping("/getMyOrder")
	public ResponseEntity<List<OrderDTO>> getmyActivityById(HttpSession session,OrderDTO orderDTO) {
		SessionLoginMemberDTO memberDTO = (SessionLoginMemberDTO) session.getAttribute("loginMember");
		if (memberDTO == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
//		List<Order> myOrder = orderService.findByActivityId(memberDTO.getMemberId());
		List<OrderDTO> myOrder = orderService.findByActivityId(memberDTO.getMemberId(), orderDTO);
		
		return ResponseEntity.ok(myOrder);
	}
	
}
