package com.ee172.team2.nemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;
import com.ee172.team2.nemo.service.MailService;
import com.ee172.team2.nemo.service.WeddingCoupleService;
import com.ee172.team2.nemo.service.WeddingGuestService;

@Controller
@RequestMapping("/weddingCouple")
public class WeddingCoupleController {

	@Autowired
	private WeddingCoupleService service;

	@Autowired
	private WeddingGuestService guestService;

	@Autowired
	private MailService mailService;

	@GetMapping
	public List<WeddingCouple> getAllCouples() {
		return service.findAll(); // 返回實體
	}

	@PostMapping("/tt") // 處理POST請求，創建新的weddingCouple
	public String createCouple(ModelMap model, @ModelAttribute WeddingCouple couple, BindingResult result) {

		WeddingCouple weddingCouple = new WeddingCouple();

		weddingCouple.setBrideName(couple.getBrideName());
		weddingCouple.setGroomName(couple.getGroomName());
		weddingCouple.setBrideParentName(couple.getBrideParentName());
		weddingCouple.setGroomParentName(couple.getBrideParentName());
		service.save(weddingCouple);
		return "redirect:/weddingCouple/getAll";
	}

	@GetMapping("/getAll") // 處理POST請求，創建新的weddingCouple
	public String getAll(ModelMap model, @ModelAttribute WeddingCouple couple, BindingResult result) {

		List<WeddingCouple> allCouples = this.getAllCouples();
		model.put("list", allCouples);
		WeddingCouple weddingCouple = new WeddingCouple();
		model.put("weddingcouple", weddingCouple);
		return "nemo/weddingCouple";
	}

	@ResponseBody
	@GetMapping("/getAll0") // 處理POST請求，創建新的weddingCouple
	public List<WeddingCouple> getAll0(ModelMap model, @ModelAttribute WeddingCouple couple, BindingResult result) {
		return this.getAllCouples();
	}

	@GetMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		try {
			service.delete(id);
			return "true";
		} catch (Exception Ex) {
			return "false";
		}

	}

	@GetMapping("/sendMail/{id}")
	@ResponseBody
	public String sendMail(@PathVariable("id") Integer id) {
//mailService.sendMail("ee172test@gmail.com", "pigtown88@gmail.com" , "婚禮邀請", "歡迎參加我們的婚禮");
		List<WeddingGuest> wgsGuests = guestService.findByWeddingId(id);
		for (WeddingGuest guest : wgsGuests) {

			try {
				mailService.sendMail("ee172test@gmail.com", guest.getEmail(), "婚禮邀請", "歡迎參加我們的婚禮");
				return "true";
			} catch (Exception Ex) {
				return "false";
			}
		}

		return "true";

	}

}
