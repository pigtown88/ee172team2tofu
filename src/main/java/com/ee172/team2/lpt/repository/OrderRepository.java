package com.ee172.team2.lpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.lpt.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
