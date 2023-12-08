package com.ee172.team2.steven.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDTO {

    private Integer id;

    private String context;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endTime;

    private String createTime;

    private Integer checkApply;

    private String applyResult;

    private String checkTime;

    private String applyType;

    private Integer empId;

    private String empName;
}
