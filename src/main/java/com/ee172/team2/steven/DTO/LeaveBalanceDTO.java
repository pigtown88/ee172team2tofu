package com.ee172.team2.steven.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveBalanceDTO {


    private String employeeId;

    private String employeeName;

    private String leaveType;

    private String leaveBalance;


}
