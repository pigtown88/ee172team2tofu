package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.ee172.team2.lpt.model.Arena;
import com.ee172.team2.lpt.service.ArenaService;


@RestController
public class ArenaController {

	@Autowired
	private ArenaService arenaService;

	@GetMapping("/arenas")
	public List<Arena> showArena(Model model) {

		List<Arena> allArena = arenaService.findArenas();

		return allArena;
	}
//	public List<Arena> showArenas() {
//		List<Arena> allArena = arenaService.findArenas();
//		return allArena;
//	}


//	@GetMapping("/arena")
//	public String showArena(Model model) {
//
//		List<Arena> allArena = arenaService.findAllArenas();
//
//		model.addAttribute("allArena", allArena);
//
//		return "/lpt/arena/arena";
//	}

	
	
	
}
