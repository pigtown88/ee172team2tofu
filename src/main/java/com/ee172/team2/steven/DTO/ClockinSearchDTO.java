package com.ee172.team2.steven.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClockinSearchDTO {
    private String empName;

    private String department;

    private Boolean late;

    private Boolean earlyLeave;

    private LocalDate startDate;

    private LocalDate endDate;


    // Getters and Setters
}
