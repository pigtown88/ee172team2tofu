package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class firstController {
	@Autowired
	private EmployeeRepository empDAO;

	@Autowired
	private EmployeeService empService;

	@GetMapping("/login")
	public String test2() {
		return "/steven/login";

	}

	@GetMapping("/register")
	public String test3() {
		return "/steven/register";

	}

	@GetMapping("/redirectRegister")
	public String redirectRegister() {
		return "redirect:/steven/register";

	}

	@GetMapping("/index")
	public String index() {
		return "/steven/index";

	}

	@GetMapping("/employeeList")
	public String employeeList() {
		return "/steven/employeeList";

	}

	@GetMapping("/employeeInfo")
	public String employeeInfo() {
		return "/steven/employeeInfo";

	}

	@GetMapping("/employeeApplyLeave")
	public String employeeApplyLeave() {
		return "/steven/employeeApplyLeave";

	}

	@GetMapping("/employeeApply")
	public String employeeApply() {
		return "/steven/employeeApply";

	}

	@GetMapping("/employeeApplyAll")
	public String employeeApplyAll() {
		return "/steven/employeeApplyAll";

	}

	@GetMapping("/clockinRecordAll")
	public String clockinRecordAll() {
		return "/steven/employeeClockinRecordAll";

	}

	@GetMapping("/clockinRecord")
	public String clockinRecord() {
		return "/steven/employeeClockinRecord";

	}

	@GetMapping("/empScheduleAll")
	public String empScheduleAll() {
		return "/steven/empScheduleAll";

	}

	@GetMapping("/empSchedule")
	public String empSchedule() {
		return "/steven/empSchedule";

	}

	@GetMapping("/empPersonalSchedule")
	public String empPersonalSchedule() {
		return "/steven/empPersonalSchedule";

	}

	@GetMapping("/chatJs")
	public String chatJs() {
		return "/steven/chatJs";

	}

	@GetMapping("/notice")
	public String notice() {
		return "/steven/notice";

	}

	@GetMapping("/employee/logout")
	public String logout(HttpSession httpSession) {


		httpSession.removeAttribute("employeeDTO");
		return "/steven/logout";
	}

}
