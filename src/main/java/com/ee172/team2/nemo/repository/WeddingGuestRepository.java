package com.ee172.team2.nemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;

public interface WeddingGuestRepository extends JpaRepository<WeddingGuest, Integer> {

	List<WeddingGuest> findByWeddingCouple(WeddingCouple weddingCouple);
   
}
