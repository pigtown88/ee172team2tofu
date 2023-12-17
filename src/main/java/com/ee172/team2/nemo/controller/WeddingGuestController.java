package com.ee172.team2.nemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;
import com.ee172.team2.nemo.service.WeddingGuestService;

import java.util.List;

@Controller
@RequestMapping("/weddingGuest")
public class WeddingGuestController {

	@Autowired
	private WeddingGuestService weddingGuestService;

	@GetMapping("/")
	public String initPage(ModelMap model, @RequestParam Integer weddingId) {
		List<WeddingGuest> wedddingGuestList = weddingGuestService.findByWeddingId(weddingId);
		model.addAttribute("list", wedddingGuestList);
		model.addAttribute("weddingId", weddingId);
		return "nemo/weddingGuestList";
	}
	
	@GetMapping("/add")
	public String add(ModelMap model, @RequestParam Integer weddingId) {
		model.addAttribute("weddingId", weddingId);
		return "nemo/weddingGuest";
	}

	@GetMapping
	public List<WeddingGuest> getAllGuests() {
		return weddingGuestService.findAll();
	}

	@GetMapping("/{id}")
	public WeddingGuest getGuestById(@PathVariable Integer id) {
		return weddingGuestService.findById(id).orElse(null);
	}

	@PostMapping
	public WeddingGuest createGuest(@RequestBody WeddingGuest guest) {
		return weddingGuestService.save(guest);
	}

	@PostMapping("/createGuest") // 創建新的weddingGuest
	public String createNewGuest(ModelMap model, @ModelAttribute WeddingGuest guest, BindingResult result) {
		weddingGuestService.save(guest);
		model.addAttribute("weddingguest", guest);
		return "redirect:/weddingGuest/?weddingId=" + guest.getWeddingCouple().getWeddingId();
	}

}