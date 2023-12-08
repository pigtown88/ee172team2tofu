package com.ee172.team2.steven.service;


import com.ee172.team2.steven.DTO.EmployeeDTO;
import com.ee172.team2.steven.DTO.EmployeeListDTO;
import com.ee172.team2.steven.DTO.LeaveBalanceDTO;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import com.ee172.team2.steven.model.LeaveTypes;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.LeaveTypesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
public class LeaveBalanceService {

    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceDAO;

    @Autowired
    private LeaveTypesRepository leaveTypesDAO;

    @Autowired
    private EmployeeRepository empDAO;





    public List<LeaveBalanceDTO> findAllLeaveBalance() {
        List<EmployeeLeaveBalance> employeeLeaveBalances = employeeLeaveBalanceDAO.findAll();
        return convertToLeaveBalanceDTOs(employeeLeaveBalances);
    }


    public List<LeaveBalanceDTO> findLeaveBalanceByEmpId(Integer empId) {
        List<EmployeeLeaveBalance> employeeLeaveBalances = employeeLeaveBalanceDAO.findByEmployee_EmpId(empId);
        return convertToLeaveBalanceDTOs(employeeLeaveBalances);
    }

    public List<LeaveBalanceDTO> findLeaveBalanceByEmpId2(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        List<EmployeeLeaveBalance> employeeLeaveBalances = employeeLeaveBalanceDAO.findByEmployee_EmpId(employeeDTO.getEmpId());
        return convertToLeaveBalanceDTOs(employeeLeaveBalances);

    }

    public List<EmployeeLeaveBalance> findAll() {
        return employeeLeaveBalanceDAO.findAll();
    }





    public Page<LeaveBalanceDTO> findLeaveBalanceByEmpId2(Integer empId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.Direction.ASC, "empId");
        Page<EmployeeLeaveBalance> employeeLeaveBalancesPage = employeeLeaveBalanceDAO.findByEmployee_EmpId(empId, pageable);
        return employeeLeaveBalancesPage.map(this::convertToLeaveBalanceDTO);
    }









    //假期餘額
    private LeaveBalanceDTO convertToLeaveBalanceDTO(EmployeeLeaveBalance employeeLeaveBalance) {
        LeaveBalanceDTO dto = new LeaveBalanceDTO();
        dto.setEmployeeId(String.valueOf(employeeLeaveBalance.getEmployee().getEmpId()));
        dto.setEmployeeName(employeeLeaveBalance.getEmployee().getEmpName());
        dto.setLeaveType(employeeLeaveBalance.getLeaveType().getLeaveType());
        dto.setLeaveBalance(String.valueOf(employeeLeaveBalance.getBalanceDays()));
        return dto;
    }


    private  List<LeaveBalanceDTO> convertToLeaveBalanceDTOs(List<EmployeeLeaveBalance> employeeLeaveBalances) {
        return employeeLeaveBalances.stream()
                .map(this::convertToLeaveBalanceDTO)
                .toList();
    }






    public void initializeEmployeeLeaveBalances(Integer empId) {
        Employee employee = empDAO.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + empId));

        List<LeaveTypes> leaveTypes = leaveTypesDAO.findAll();

        for (LeaveTypes type : leaveTypes) {
            EmployeeLeaveBalance balance = new EmployeeLeaveBalance();
            balance.setEmployee(employee);
            balance.setLeaveType(type);
            balance.setYear(LocalDate.now().getYear());
            balance.setBalanceDays(calculateInitialLeaveDays(type, employee));

            employeeLeaveBalanceDAO.save(balance);
        }
    }

    public void initializeEmployeeLeaveBalances(Employee employee) {
        // 假設 leaveTypesRepository 可以獲取所有假期類型
        List<LeaveTypes> leaveTypes = leaveTypesDAO.findAll();

        // 為每個假期類型創建一個假期餘額記錄
        for (LeaveTypes type : leaveTypes) {
            EmployeeLeaveBalance balance = new EmployeeLeaveBalance();
            balance.setEmployee(employee);
            balance.setLeaveType(type);
            balance.setYear(LocalDate.now().getYear());
            balance.setBalanceDays(calculateInitialLeaveDays(type, employee));

            employeeLeaveBalanceDAO.save(balance);
        }
    }



    private double calculateInitialLeaveDays(LeaveTypes type, Employee employee) {
        if (type.getLeaveType().equals("事假") || type.getLeaveType().equals("病假")) {
            return type.getYearlyLimit();
        }

        if (type.getLeaveType().equals("特別休假")) {
            // 將 java.util.Date 轉換為 LocalDate
            LocalDate startWorkDate = employee.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();

            // 計算工作年限
            int yearsOfWork = Period.between(startWorkDate, currentDate).getYears();

            if (yearsOfWork <= 3) {
                return 6;
            } else {
                return 6 + (yearsOfWork - 3) * 2;
            }
        }

        return 0;
    }






}

