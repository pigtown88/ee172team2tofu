package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.DTO.*;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Apply;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import com.ee172.team2.steven.repository.ApplyRepository;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.service.LeaveApplicationService;
import com.ee172.team2.steven.service.LeaveBalanceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api/leave")
public class LeaveController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @Autowired
    private LeaveApplicationService leaveApplicationService;
@Autowired
    private EmployeeLeaveBalanceRepository leaveBalanceDAO;
@Autowired
private ApplyRepository applyDAO;



//顯示當前員工剩餘假期
@GetMapping("/leaveBalance")
private ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalanceByEmpId(HttpSession session){
    EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
    if (employeeDTO == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<LeaveBalanceDTO> leaveBalanceDTOs = leaveBalanceService.findLeaveBalanceByEmpId(employeeDTO.getEmpId());
    return ResponseEntity.ok(leaveBalanceDTOs);
}


private ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalanceByEmpId2(HttpSession session){
    EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
    if (employeeDTO == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<LeaveBalanceDTO> leaveBalanceDTOs = leaveBalanceService.findLeaveBalanceByEmpId(employeeDTO.getEmpId());
    return ResponseEntity.ok(leaveBalanceDTOs);
}



    @GetMapping("/leaveBalance/{empId}")
    public ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalanceByEmpId(@PathVariable Integer empId) {
        List<LeaveBalanceDTO> leaveBalanceDTOs = leaveBalanceService.findLeaveBalanceByEmpId(empId);
        return ResponseEntity.ok(leaveBalanceDTOs);

    }


        @GetMapping("/leaveBalance/all")
private ResponseEntity<List<LeaveBalanceDTO>> getAllLeaveBalance(){
    List<LeaveBalanceDTO> leaveBalanceDTOs = leaveBalanceService.findAllLeaveBalance();
    return ResponseEntity.ok(leaveBalanceDTOs);
}



//渲染所有人的假單
    @GetMapping("/leaveApply/All")
    public Page<ApplyDTO> getApply(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
        return leaveApplicationService.findByPage(pageNumber);
    }



//    渲染個人請假申請
@GetMapping("/leaveApply/Employee")

public Page<ApplyDTO> findApplyByEmpId(HttpSession session, Integer pageNumber){
    EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
    if (employeeDTO == null) {
        return null;
    }
    Integer empId = employeeDTO.getEmpId();
   return leaveApplicationService.findApplyByEmpId(empId,pageNumber);

}

//    取消假期申請
    @PostMapping("/cancelLeaveApplication")
    public ResponseEntity<?> cancelLeaveApplication(@RequestBody Map<String, Integer> payload) {
        Integer applyId = payload.get("applyId");
        if (applyId == null) {
            return ResponseEntity.badRequest().body("Missing applyId");
        }

        try {
            leaveApplicationService.cancelApplication(applyId);
            return ResponseEntity.ok("Application cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error cancelling application: " + e.getMessage());
        }
    }







//    員工提交假單
    @PostMapping("/applyLeave")
    public ResponseEntity<?> applyForLeave(@RequestBody Apply leaveApplication) {
        // 驗證請假時間
        if (leaveApplication.getStartTime().after(leaveApplication.getEndTime())) {
            return ResponseEntity.badRequest().body("請假結束時間必須晚於開始時間");
        }

        // 計算請假天數
        double leaveDays = leaveApplicationService.calculateLeaveDays(leaveApplication.getStartTime(), leaveApplication.getEndTime());

        // 檢查假期餘額
        EmployeeLeaveBalance balance = leaveBalanceDAO.findByEmployeeAndLeaveType(
                leaveApplication.getEmployee(), leaveApplication.getLeaveType());

        if (balance.getBalanceDays() < leaveDays) {
            return ResponseEntity.badRequest().body("假期餘額不足");
        }

        // 處理請假申請
        leaveApplicationService.processLeaveApplication(leaveApplication, leaveDays);

        return ResponseEntity.ok().body("請假申請已提交，等待審核");
    }


    @GetMapping("/applications/pending")
    public ResponseEntity<List<Apply>> getPendingApplications() {
        List<Apply> pendingApplications = applyDAO.findByCheckApply(0);
        return ResponseEntity.ok(pendingApplications);
    }



    @PostMapping("/applications/reject/{applicationId}")
    public ResponseEntity<String> rejectApplication(@PathVariable int applicationId) {
        try {
            leaveApplicationService.rejectLeaveApplication(applicationId);
            return ResponseEntity.ok().body("Application rejected successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



//    審核假單通過
    @PostMapping("/applications/approve/{applicationId}")
    public ResponseEntity<String> approveLeaveApplication(@PathVariable int applicationId) {
        try {
            leaveApplicationService.processLeaveApplicationApproval(applicationId, true); // 假定总是批准
            return ResponseEntity.ok("申請已處理");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
