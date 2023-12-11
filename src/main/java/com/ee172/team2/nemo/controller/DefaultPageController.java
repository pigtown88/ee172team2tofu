package com.ee172.team2.nemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.service.WeddingCoupleService;

@Controller
public class DefaultPageController {
	@Autowired
	private WeddingCoupleService service;
	
	@RequestMapping("/webc")
	public String toDefaultPage(ModelMap model) {
		List<WeddingCouple> allCouples = service.findAll();
		model.put("s", allCouples);
		WeddingCouple weddingCouple = new WeddingCouple();
		model.put("weddingcouple", weddingCouple);
		return "nemo/weddingCouplemanage";
	}

	@RequestMapping("/weddingCouplemanage")
    public String test(){
        return "nemo/weddingCouplemanage";

    }
	
	   @GetMapping("/nemoindex")
	    public String index(){
	        return "/nemo/index";

	    }
}
