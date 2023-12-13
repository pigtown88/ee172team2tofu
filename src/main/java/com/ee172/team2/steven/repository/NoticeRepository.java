package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {


    List<Notice> findByEmployeeEmpId(int empId);

    List<Notice> findByEmployeeEmpId(int empId, Pageable pageable);
}
