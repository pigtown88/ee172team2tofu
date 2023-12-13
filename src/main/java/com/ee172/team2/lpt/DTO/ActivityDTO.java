package com.ee172.team2.lpt.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

	private String activityId;

	private String activityName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private String activityDayStart;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private String activityDayEnd;

	private Integer activityPrice;
	
	private String activityType;

	private String activityIntro;
	
	private String reserveId;
	
	private String memberId;
	
	private String memberName;

}
