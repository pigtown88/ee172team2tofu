package com.ee172.team2.lpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.lpt.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	boolean existsByMember_MemberIdAndActivity_ActivityId(Integer memberId, Integer activityId);

	List<Order> findByMember_memberId(Integer memberId);

}