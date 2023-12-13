package com.ee172.team2.steven.controller;


import com.ee172.team2.steven.DTO.EmployeeDTO;
import com.ee172.team2.steven.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backstage/employee")
public class NoticeController {

@Autowired
private NoticeService noticeService;





    @GetMapping("/notice")
    public ResponseEntity<?> getNoticeByEmpId(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        return ResponseEntity.ok(noticeService.findByEmpId(employeeDTO.getEmpId()));
    }


@GetMapping("/notice5")
    public ResponseEntity<?> getNoticeByEmpId2(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");

        }
        return ResponseEntity.ok(noticeService.findTop5Notices(employeeDTO.getEmpId()));
    }





    }
