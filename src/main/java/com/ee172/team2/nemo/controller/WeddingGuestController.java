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
		model.addAttribute("filter_guestName", "");
		model.addAttribute("filter_phonenumber", "");
		return "nemo/weddingGuestList";
	}

	@GetMapping("/add")
	public String add(ModelMap model, @RequestParam Integer weddingId) {
		WeddingCouple couple = new WeddingCouple();
		couple.setWeddingId(weddingId);
		WeddingGuest guest = new WeddingGuest();
		guest.setWeddingCouple(couple);
		model.addAttribute("weddingguest", guest);
		return "nemo/weddingGuest";
	}

	@GetMapping("/update")
	public String update(ModelMap model, @RequestParam Integer guestId) {
		WeddingGuest guest = weddingGuestService.findById(guestId).get();
		model.addAttribute("weddingguest", guest);
		return "nemo/weddingGuest";
	}

	@GetMapping("/delete")
	public String delete(ModelMap model, @RequestParam Integer guestId) {
		WeddingGuest guest = weddingGuestService.findById(guestId).get();
		weddingGuestService.delete(guestId);
		return "redirect:/weddingGuest/?weddingId=" + guest.getWeddingCouple().getWeddingId();
	}

	/**
	 * 按條件查詢
	 * 
	 * @param model
	 * @param guestName
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("/queryList")
	public String getByCondition(ModelMap model, @RequestParam String weddingId,
			@RequestParam String filter_guestName) {
		List<WeddingGuest> guests = weddingGuestService.findByCondition(weddingId, filter_guestName);
		model.addAttribute("list", guests);
		model.addAttribute("weddingId", weddingId);
		model.addAttribute("filter_guestName", filter_guestName);
		return "nemo/weddingGuestList";
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
		return "redirect:/weddingGuest/?weddingId=" + guest.getWeddingCouple().getWeddingId();
	}

}