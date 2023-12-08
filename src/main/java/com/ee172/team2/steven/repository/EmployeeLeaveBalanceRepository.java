package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import com.ee172.team2.steven.model.LeaveTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance,Integer> {

    Optional<EmployeeLeaveBalance> findByEmployeeAndLeaveTypeAndYear(Employee employee, LeaveTypes leaveType, int year);


    EmployeeLeaveBalance findByEmployeeAndLeaveType(Employee employee, LeaveTypes leaveType);

    List<EmployeeLeaveBalance> findByEmployee_EmpId(Integer empId);


    Page<EmployeeLeaveBalance> findByEmployee_EmpId(Integer empId, Pageable pageable);


}
