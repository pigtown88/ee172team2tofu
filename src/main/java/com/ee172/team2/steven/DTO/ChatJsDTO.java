package com.ee172.team2.steven.DTO;


import lombok.Data;

@Data
public class ChatJsDTO {

    private String empName;

    private Integer late = 0;

    private Integer earlyLeave = 0;

    private Integer workOverTime = 0;


}
