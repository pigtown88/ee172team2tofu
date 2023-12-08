package com.ee172.team2.steven.DTO;

import com.ee172.team2.steven.model.Clockin;
import com.ee172.team2.steven.model.Employee;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClockinSpecifications {

    public static Specification<Clockin> hasEmpName(String empName) {
        return (root, query, cb) -> {
            if (empName == null || empName.isEmpty()) {
                return cb.conjunction();
            }
            Join<Clockin, Employee> employeeJoin = root.join("employee");
            return cb.like(root.get("employee").get("empName"), "%" + empName + "%");
        };
    }

    public static Specification<Clockin> hasDepartment(String department) {
        return (root, query, cb) -> {
            if (department == null || department.isEmpty()) {
                return cb.conjunction();
            }
            Join<Clockin, Employee> employeeJoin = root.join("employee");
            return cb.equal(root.get("employee").get("department").get("depName"), department);
        };
    }

    public static Specification<Clockin> isLate(Boolean isLate) {
        return (root, query, cb) -> {
            if (isLate == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("Late"), isLate);
        };
    }

    public static Specification<Clockin> earlyLeave(Boolean earlyLeave) {
        return (root, query, cb) -> {
            if (earlyLeave == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("earlyLeave"), earlyLeave);
        };
    }

    public static Specification<Clockin> betweenDates(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get("day"), startDate, endDate);
        };
    }
}
