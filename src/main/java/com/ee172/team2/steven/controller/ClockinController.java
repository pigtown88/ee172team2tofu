package com.ee172.team2.steven.controller;


import com.ee172.team2.steven.DTO.ApplyDTO;
import com.ee172.team2.steven.DTO.ClockinDTO;
import com.ee172.team2.steven.DTO.ClockinSearchDTO;
import com.ee172.team2.steven.DTO.EmployeeDTO;
import com.ee172.team2.steven.model.Clockin;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.service.ClockinService;
import com.ee172.team2.steven.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backstage/employee")
public class ClockinController {


    @Autowired
    private ClockinService clockinService;

    @Autowired
    private EmployeeService empService;




    @PostMapping("/search")
    public ResponseEntity<?> searchClockinRecords(@RequestBody ClockinSearchDTO searchDTO,
                                                  @RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
        System.out.println("Received search parameters: " + searchDTO);
        try {

            Page<ClockinDTO> records = clockinService.searchClockinRecords(searchDTO, pageNumber);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during search: " + e.getMessage());
        }
    }



    @GetMapping("/clockin/employee")
    public ResponseEntity<?> getClockinRecord(HttpSession session) {

        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Page<ClockinDTO> clockin = clockinService.findByPageAndEmpId(employeeDTO.getEmpId(),1);


        return ResponseEntity.ok(clockin);
    }




    @GetMapping("/clokin/All")
    public Page<ClockinDTO> getClockinRecordAll(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
        return clockinService.findByPage(pageNumber);
    }


    @PostMapping("/clockin")
    public ResponseEntity<?> clockIn(HttpSession session) {

        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Employee employee = empService.getEmployeeById4(employeeDTO.getEmpId());
        ClockinDTO clockin = ConverseClockinToClockinDTO(clockinService.clockIn(employee));


        return ResponseEntity.ok(clockin);
    }

    @PostMapping("/clockout")
    public ResponseEntity<?> clockOut(HttpSession session) {

        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Employee employee = empService.getEmployeeById4(employeeDTO.getEmpId());
        ClockinDTO clockout = ConverseClockoutToClockinDTO(clockinService.clockOut(employee));
        return ResponseEntity.ok(clockout);
    }






    public ClockinDTO ConverseClockinToClockinDTO(Clockin clockin){
        ClockinDTO clockinDTO = new ClockinDTO();

        clockinDTO.setEmpName(clockin.getEmployee().getEmpName());
        clockinDTO.setEmpId(String.valueOf(clockin.getEmployee().getEmpId()));
        clockinDTO.setClockinTime(String.valueOf(clockin.getClockinTime()));
        clockinDTO.setClockoutTime(String.valueOf(clockin.getClockoutTime()));
        clockinDTO.setDay(String.valueOf(clockin.getDay()));
        clockinDTO.setDepartment((clockin.getEmployee().getDepartment().getDepName()));
        clockinDTO.setLate(clockin.isLate());
        clockinDTO.setEarlyLeave(clockin.isEarlyLeave());
        return clockinDTO;




    }

    public ClockinDTO ConverseClockoutToClockinDTO(Clockin clockin){
        ClockinDTO clockinDTO = new ClockinDTO();

        clockinDTO.setEmpName(clockin.getEmployee().getEmpName());
        clockinDTO.setEmpId(String.valueOf(clockin.getEmployee().getEmpId()));
        clockinDTO.setClockinTime(String.valueOf(clockin.getClockinTime()));
        clockinDTO.setClockoutTime(String.valueOf(clockin.getClockoutTime()));
        clockinDTO.setDay(String.valueOf(clockin.getDay()));
        clockinDTO.setDepartment((clockin.getEmployee().getDepartment().getDepName()));
        clockinDTO.setLate(clockin.isLate());
        clockinDTO.setEarlyLeave(clockin.isEarlyLeave());
        return clockinDTO;




    }


}
