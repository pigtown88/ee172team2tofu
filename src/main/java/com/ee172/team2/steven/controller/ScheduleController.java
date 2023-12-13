package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.DTO.EmpScheduleDTO;
import com.ee172.team2.steven.DTO.EmpSchedulesDTO;
import com.ee172.team2.steven.DTO.EmployeeDTO;
import com.ee172.team2.steven.DTO.ScheduleDTO;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.ScheduleManager;
import com.ee172.team2.steven.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/backstage/employee")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;





//    @PostMapping("/addSchedules")
//    public ResponseEntity<?> addSchedules(@RequestBody EmpSchedulesDTO empSchedulesDTO) {
//        try {
//            Date day = convertToDate(EmpSchedulesDTO.getDay());
//            List<ScheduleManager> schedules = new ArrayList<>();
//            for (Integer empId : empSchedulesDTO.getEmpId()) {
//                ScheduleManager schedule = scheduleService.addSchedule(empId, day);
//                schedules.add(schedule);
//            }
//            return new ResponseEntity<>(schedules, HttpStatus.CREATED);
//        } catch (BusinessException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }



    @GetMapping("/empPersonalSchedule")
    public ResponseEntity<List<ScheduleDTO>> getScheduleEvents(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<ScheduleDTO> scheduleEvents = scheduleService.getEmpScheduleById2(employeeDTO.getEmpId());
        return ResponseEntity.ok(scheduleEvents);
    }

    @GetMapping("/empPersonalForSchedule")
    public ResponseEntity<List<EmpScheduleDTO>> getEmpPersonalSchedule(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<EmpScheduleDTO> empSchedule = scheduleService.getEmpScheduleById(employeeDTO.getEmpId());
        return ResponseEntity.ok(empSchedule);
    }





    //FC拿班表
    @GetMapping("/empForSchedule")
    public ResponseEntity<List<EmpScheduleDTO>> getEmpForSchedule() {
        List<EmpScheduleDTO> empSchedule = scheduleService.getEmpSchedule();
        return ResponseEntity.ok(empSchedule);
    }





    //FC拿班表
@GetMapping("/employeeSchedule")
    public ResponseEntity<List<ScheduleDTO>> getAllScheduleEvents() {
        List<ScheduleDTO> scheduleEvents = scheduleService.getScheduleEvents();
        return ResponseEntity.ok(scheduleEvents);
    }


    @GetMapping
    public ResponseEntity<List<ScheduleManager>> getScheduleByDateRange(
            @RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(scheduleService.getScheduleByDateRange(startDate, endDate));
    }



    //加入班表
    @PostMapping("/addSchedule")
    public ResponseEntity<?> addSchedule(@RequestBody EmpScheduleDTO empScheduleDTO) {
        try {
            Date day = convertToDate(empScheduleDTO.getDay());
            ScheduleManager scheduleManager = scheduleService.addSchedule(
                   empScheduleDTO.getEmpId(),

                    day);
            return new ResponseEntity<>(scheduleManager, HttpStatus.CREATED);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private Date convertToDate(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    //刪除班表
    @DeleteMapping("/deleteShift/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }

}
