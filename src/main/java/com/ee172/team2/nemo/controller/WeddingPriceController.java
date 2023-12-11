package com.ee172.team2.nemo.controller;

import com.ee172.team2.nemo.model.WeddingPrice;
import com.ee172.team2.nemo.service.WeddingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/price")
public class WeddingPriceController {

    @Autowired
    private WeddingPriceService weddingPriceService;

    @GetMapping("/choosePlan")
    public String choosePlan(Model model) {
        model.addAttribute("weddingPrices", weddingPriceService.findAll());
        return "nemo/choosePlan";
    }

    @PostMapping("/submitPlan")
    public String submitPlan(@RequestParam(name = "selectedPlan") String selectedPlan, Model model) {
        // 处理选择的方案
        // 此处可添加具体逻辑
        model.addAttribute("selectedPlan", selectedPlan);
        return "nemo/planResult";
    }
}
