package com.ee172.team2.nemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;
import com.ee172.team2.nemo.model.WeddingPrice;
import com.ee172.team2.nemo.service.WeddingCoupleService;
import com.ee172.team2.nemo.service.WeddingPriceService;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/weddingPrice")
public class WeddingPriceController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WeddingPriceService weddingPriceService;

	@Autowired
	WeddingCoupleService weddingCoupleService;

	@GetMapping("/")
	public String initPage(ModelMap model, @RequestParam Integer weddingId) {
		List<WeddingPrice> weddingPriceList = weddingPriceService.findAll();
		model.addAttribute("list", weddingPriceList);

		WeddingCouple weddingCouple = new WeddingCouple();
		weddingCouple.setWeddingId(weddingId);
		model.addAttribute("weddingcouple", weddingCouple);
		return "nemo/price2";
	}

	@RequestMapping("/result")
	public String result(ModelMap model, @RequestParam Integer weddingId) {
		WeddingCouple weddingCouple = weddingCoupleService.findById(weddingId).get();
		WeddingPrice weddingPrice = weddingPriceService.findById(weddingCouple.getPriceId()).get();
		model.addAttribute("selectedPlan", weddingPrice.getName());
		return "nemo/planResult";
	}

	/**
	 * 更新价格方案
	 */
	@ResponseBody
	@PostMapping("/updateWeddingPrice")
	public String updateWeddingPrice(@RequestBody Map<String, String> param) {
		String weddingId = param.get("weddingId");
		String priceId = param.get("priceId");

		WeddingCouple weddingCouple = weddingCoupleService.findById(Integer.parseInt(weddingId)).get();
		weddingCouple.setWeddingId(Integer.parseInt(weddingId));
		weddingCouple.setPriceId(Integer.parseInt(priceId));

		// 更新priceID
		weddingCoupleService.save(weddingCouple);

		return "true";
	}

}
