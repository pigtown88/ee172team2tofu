package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.DTO.ClockinDTO;
import com.ee172.team2.steven.model.Apply;
import com.ee172.team2.steven.model.Clockin;
import com.ee172.team2.steven.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface ClockinRepository extends JpaRepository<Clockin,Integer>{

    @Query(value = "SELECT * FROM clockin WHERE empId = ?1 ORDER BY clockinTime DESC LIMIT 1", nativeQuery = true)
    Optional<Clockin> findLatestClockinRecordByEmployee(Integer empId);

    @Query("SELECT c FROM Clockin c WHERE c.employee = :employee ORDER BY c.clockinTime DESC")
    Optional<Clockin> findLatestClockinRecordByEmployee(@Param("employee") Employee employee);




  Optional <Clockin> findByEmployeeAndDay(Employee employee, Date day);

    @Query("SELECT c FROM Clockin c WHERE c.employee = :employee AND c.day = :day ORDER BY c.clockinTime DESC")
    Optional<Clockin> findLatestClockinRecordByEmployeeAndDay(@Param("employee") Employee employee, @Param("day") Date day);


    Page<Clockin> findAll(Pageable pageable);

    Page<Clockin> findByEmployee_EmpId(Integer empId, Pageable pageable);


    Page<Clockin> findByEmployee_EmpIdAndDay(Integer empId, Date day, Pageable pageable);

    Page<Clockin> findByEmployee_EmpIdAndDayBetween(Integer empId, Date dayStart, Date dayEnd, Pageable pageable);

    Page<Clockin> findByEmployee_EmpIdAndDayAfter(Integer empId, Date day, Pageable pageable);

    Page<Clockin> findByEmployee_EmpIdAndDayBefore(Integer empId, Date day, Pageable pageable);

    Page<Clockin> findAllByLateIsTrue(Pageable pageable);

    Page<Clockin> findAllByEarlyLeaveIsTrue(Pageable pageable);

    Page<Clockin> findAllByLateIsTrueAndEarlyLeaveIsTrue(Pageable pageable);


    Page<Clockin> findAll(Specification<Clockin> spec, Pageable pageable);
}
