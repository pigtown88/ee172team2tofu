package com.ee172.team2.lpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ee172.team2.lpt.model.Arena;

public interface ArenaRepository extends JpaRepository<Arena, Integer> {

	@Query(value="SELECT * FROM arena ORDER BY arenaId DESC LIMIT 6",nativeQuery = true)
	 public List<Arena> findTheLatesArena();
}
