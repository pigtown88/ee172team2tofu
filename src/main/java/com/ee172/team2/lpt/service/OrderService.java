package com.ee172.team2.lpt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;
import com.ee172.team2.lpt.model.Activity;
import com.ee172.team2.lpt.model.Order;
import com.ee172.team2.lpt.repository.ActivityRepository;
import com.ee172.team2.lpt.repository.OrderRepository;
import com.ee172.team2.patty.DTO.OrderDTO;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
//	@Autowired
//	private OrderDTO orderDTO;
	
	public void addOrder ( Integer memberId,Integer activityId) {
		
//		if (orderRepository.existsByMember_MemberIdAndActivity_ActivityId(memberId, activityId)) {
//            throw new IllegalArgumentException("该会员已经报名过该活动");
//        }
		
		Member member = memberRepository.findById(memberId).get();
		
		Activity activity = activityRepository.findById(activityId).get();
		
		Order order = new Order();
		order.setActivity(activity);
		order.setMember(member);
		

		
		
		
		orderRepository.save(order);
		
	}
	
	public List<OrderDTO> findByActivityId(Integer memberId,OrderDTO orderDTO) {
		List<Order> Order = orderRepository.findByMember_memberId(memberId);
		List<OrderDTO> myOrdersList =new ArrayList<>();
		for(Order o :Order) {
			OrderDTO myOrder = new OrderDTO();
			myOrder.setActivityName(o.getActivity().getActivityName());
			myOrder.setActivityDayStart(String.valueOf(o.getActivity().getActivityDayStart()));
			myOrder.setActivityDayEnd(String.valueOf(o.getActivity().getActivityDayEnd()));
			myOrder.setActivityPrice(String.valueOf(o.getActivity().getActivityPrice()));
			myOrdersList.add(myOrder);
		}
		return myOrdersList;
	}
	
	public void deleteMyOrder(Integer memberId) {
		orderRepository.deleteById(memberId);
	}
	

}
