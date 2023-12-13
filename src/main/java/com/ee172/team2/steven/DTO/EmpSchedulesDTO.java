package com.ee172.team2.steven.DTO;

import lombok.Data;

import java.util.List;

@Data
public class EmpSchedulesDTO {

    private List<Integer> empId;
    private List<String> empName;
    private List<Integer> shiftType;
    private List<String> day;

}
