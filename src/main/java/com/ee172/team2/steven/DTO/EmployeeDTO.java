package com.ee172.team2.steven.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Integer empId;

	private String empName;

	private String position;

	private String department;

	private Integer role; // 權限等級

}
