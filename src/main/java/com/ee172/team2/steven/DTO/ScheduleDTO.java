package com.ee172.team2.steven.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class ScheduleDTO {

    private Integer empId;
    private String empName;
    private Integer scheduleId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;

}
