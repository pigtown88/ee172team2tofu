package com.ee172.team2.steven.DTO;

import lombok.Data;

@Data
public class ClockinDTO {

    private String empId;

    private String empName;

    private String department;

    private String clockinTime;

    private String clockoutTime;

    private String day;

    private boolean late;

    private boolean earlyLeave;

    private String workOvertime;

    private String workTime;

    private String workStatus;


}
