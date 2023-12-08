package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Apply;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply,Integer> {

    List<Apply> findByCheckApply(Integer checkApplyStatus);



    List<Apply> findAll();

    Page<Apply> findByEmployee_EmpId(Integer empId, Pageable pageable);

}
