package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class firstController {
    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private EmployeeService empService;




    @GetMapping("/123")
    public String test(){
        return "/steven/auth-login";

    }

    @GetMapping("/login")
    public String test2(){
        return "/steven/login";

    }
    @GetMapping("/register")
    public String test3(){
        return "/steven/register";

    }
    @GetMapping("/redirectRegister")
    public String redirectRegister(){
        return "redirect:/steven/register";

    }
    @GetMapping("/index")
    public String index(){
        return "/steven/index";

    }

    @GetMapping("/test/employee")
    public String employee(){
        return "/steven/employee";

    }

    @GetMapping("/index2")
    public String index2(){
        return "/steven/index2";

    }
    @GetMapping("/employeeData")
    public String empData(){
        return "/steven/employeeData";

    }

    @GetMapping("/employeeData2")
    public String empData2(){
        return "/steven/employeeData2";

    }

    @GetMapping("/employeeList")
    public String employeeList(){
        return "/steven/employeeList";

    }

    @GetMapping("/employeeInfo")
    public String employeeInfo(){
        return "/steven/employeeInfo";

    }

    @GetMapping("/employeeApplyLeave")
    public String employeeApplyLeave(){
        return "/steven/employeeApplyLeave";

    }

    @GetMapping("/employeeApply")
    public String employeeApply(){
        return "/steven/employeeApply";

    }
    @GetMapping("/employeeApplyAll")
    public String employeeApplyAll(){
        return "/steven/employeeApplyAll";

    }

    @GetMapping("/clockinRecordAll")
    public String clockinRecordAll(){
        return "/steven/employeeClockinRecordAll";

    }


    @GetMapping("/clockinRecord")
    public String clockinRecord(){
        return "/steven/employeeClockinRecord";

    }


    @GetMapping("/empSchedule")
    public String empSchedule(){
        return "/steven/empScheduleAll";

    }










    @GetMapping("/home")
    public String home(){
        return "/public/home";

    }

    @GetMapping("/empList")
    public String showEmployees(@RequestParam(name="p", defaultValue = "1") Integer pageNumber, Model model) {
        Page<Employee> page = empService.findByPage(pageNumber);

        model.addAttribute("page", page);

        return "steven/employeeData";
    }


}
