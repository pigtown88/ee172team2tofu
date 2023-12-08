package com.ee172.team2.steven.service;

import com.ee172.team2.steven.DTO.ApplyDTO;
import com.ee172.team2.steven.DTO.EmployeeListDTO;
import com.ee172.team2.steven.DTO.LeaveBalanceDTO;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.*;
import com.ee172.team2.steven.repository.ApplyRepository;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.repository.NoticeRepository;
import com.ee172.team2.steven.repository.ScheduleManagerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LeaveApplicationService {
@Autowired
    private ScheduleManagerRepository scheduleManagerRepository;
@Autowired
    private ApplyRepository applyDAO;
    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceDAO;
    @Autowired
    private NoticeRepository noticeDAO;

    @Autowired
    NoticeService noticeService;


//找全部假單
    public Page<ApplyDTO> findByPage(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "createTime");
        Page<Apply> applyPage = applyDAO.findAll(pageable);


        Page<ApplyDTO> dtoPage = applyPage.map(this::convertoApplyDTO);

        return dtoPage;
    }







//找個人假單
    public Page<ApplyDTO> findApplyByEmpId(Integer empId,Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber -1,5, Sort.Direction.DESC, "createTime");
        Page<Apply> applyPage = applyDAO.findByEmployee_EmpId(empId,pageable);
        return applyPage.map(this::convertoApplyDTO);




    }


//    取消申請
    @Transactional
    public void cancelApplication(Integer applyId) throws Exception {
        Apply application = applyDAO.findById(applyId)
                .orElseThrow(() -> new Exception("Application not found with id: " + applyId));

        if (application.getCheckApply() == 0) {

            application.setCheckApply(4);
            application.setApplyResult("已取消");
            application.setCheckTime(new Timestamp(System.currentTimeMillis()));
            applyDAO.save(application);
        } else {
            throw new Exception("Application cannot be cancelled in its current state");
        }
    }



//DTO轉換
    private ApplyDTO convertoApplyDTO(Apply apply) {
        ApplyDTO dto = new ApplyDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dto.setId(apply.getId());
        dto.setEmpId(apply.getEmployee().getEmpId());
        dto.setEmpName(apply.getEmployee().getEmpName());
        dto.setApplyResult(String.valueOf(apply.getCheckApply()));
        dto.setApplyType(String.valueOf(apply.getLeaveType().getLeaveType()));
        dto.setStartTime(formatTimestamp(apply.getStartTime(), "yyyy-MM-dd"));
        dto.setEndTime(formatTimestamp(apply.getEndTime(), "yyyy-MM-dd"));
        dto.setCreateTime(formatTimestamp(apply.getCreateTime(), "yyyy-MM-dd-HH:mm"));
        dto.setCheckTime(formatTimestamp(apply.getCheckTime(), "yyyy-MM-dd-HH:mm"));
        dto.setContext(apply.getContext());
        dto.setCheckApply(apply.getCheckApply());

        return dto;
    }


    public String formatTimestamp(Timestamp timestamp, String pattern) {
        if (timestamp == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        return dateTime.format(formatter);
    }



//拒絕請假申請

    public void rejectLeaveApplication(Integer applicationId) {
        Apply application = applyDAO.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        // 設置狀態為駁回
        application.setCheckApply(2); //
        application.setApplyResult("未通過");
        application.setCheckTime(new Timestamp(System.currentTimeMillis()));
        applyDAO.save(application);
    }



//    發送請假請求

    public void processLeaveApplication(Apply leaveApplication, double leaveDays) {
        // 獲取員工和假期類型
        Employee employee = leaveApplication.getEmployee();
        LeaveTypes leaveType = leaveApplication.getLeaveType();

        // 查找當前的假期餘額
        EmployeeLeaveBalance balance = employeeLeaveBalanceDAO.findByEmployeeAndLeaveType(employee, leaveType);
        if (balance == null) {
            throw new BusinessException("沒有找到對應的假期餘額記錄");
        }

        // 檢查假期餘額是否足夠
        if (balance.getBalanceDays() < leaveDays) {
            // 創建並發送不足通知
            noticeService.createShortageNotice(employee, leaveType, leaveDays);
            return; // 終止進一步處理
        }

//        // 更新假期餘額
//        balance.setBalanceDays(balance.getBalanceDays() - leaveDays);
//        employeeLeaveBalanceDAO.save(balance);

        leaveApplication.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 保存請假申請
        leaveApplication.setCheckApply(0); // 0 表示待審核Z
        applyDAO.save(leaveApplication);
    }


//    通過請假申請
    public void processLeaveApplicationApproval(int applicationId, boolean isApproved) {
        Apply leaveApplication = applyDAO.findById(applicationId)
                .orElseThrow(() -> new BusinessException("請假申請未找到"));

        if (isApproved) {
            // 查找當前的假期餘額
            Employee employee = leaveApplication.getEmployee();
            LeaveTypes leaveType = leaveApplication.getLeaveType();
            EmployeeLeaveBalance balance = employeeLeaveBalanceDAO.findByEmployeeAndLeaveType(employee, leaveType);

            if (balance == null) {
                throw new BusinessException("沒有找到對應的假期餘額記錄");
            }

            // 計算請假天數
            double leaveDays = calculateLeaveDays(leaveApplication.getStartTime(), leaveApplication.getEndTime());

            // 更新假期餘額
            if (balance.getBalanceDays() >= leaveDays) {
                balance.setBalanceDays(balance.getBalanceDays() - leaveDays);
                employeeLeaveBalanceDAO.save(balance);



            } else {
                noticeService.createApplicationRejectedNotice(employee,leaveApplication);

            }
        }

        // 更新申請狀態
        leaveApplication.setCheckApply(isApproved ? 1 : 2);//1代表通過 2代表未過
        leaveApplication.setApplyResult(isApproved ? "通過" : "未通過");
        leaveApplication.setCheckTime(new Timestamp(System.currentTimeMillis()));
        applyDAO.save(leaveApplication);
    }





//計算請假天數
    public double calculateLeaveDays(Timestamp startTime, Timestamp endTime) {
        // 轉換 Timestamp 為 LocalDate
        LocalDate start = startTime.toLocalDateTime().toLocalDate();
        LocalDate end = endTime.toLocalDateTime().toLocalDate();

        long days = ChronoUnit.DAYS.between(start, end);

        // 假設每週工作 5 天，週末不計入請假天數
        long businessDays = Stream.iterate(start, date -> date.plusDays(1))
                .limit(days)
                .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY))
                .count();

        return (double) businessDays+1;//時間邏輯
    }





    private void createNoticeForEmployee(Employee employee, String title, String message) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setTitle(title);
        notice.setContext(message);
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        noticeDAO.save(notice);
    }
}