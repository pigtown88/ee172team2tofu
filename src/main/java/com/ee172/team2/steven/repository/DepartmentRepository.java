package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
