package com.ee172.team2.lpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LptFirstController {


	@GetMapping("/arenaReserved")
    public String arenaReserved(){
        return "/lpt/reserve/arenaReserved";
    } 
	
	@GetMapping("/addReserve")
    public String addReserve(){
        return "/lpt/reserve/addReserve";
    } 
	
	
}
