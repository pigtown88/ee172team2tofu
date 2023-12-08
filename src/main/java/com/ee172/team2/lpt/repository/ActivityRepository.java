
package com.ee172.team2.lpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;



import com.ee172.team2.lpt.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	List<Activity> findByReserveReserveId(Integer reserveId);
	
	@Query(value="SELECT * FROM activity ORDER BY activityId DESC LIMIT 20",nativeQuery = true)
	 public List<Activity> findTheLatesActivity();

	
}


