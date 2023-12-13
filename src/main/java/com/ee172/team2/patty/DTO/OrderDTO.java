package com.ee172.team2.patty.DTO;

import java.sql.Timestamp;

import com.ee172.team2.lpt.model.Activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private String activityName;
	private String activityDayStart;
	private String activityDayEnd;
	private String activityPrice;
}
