package com.ee172.team2.patty.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class showFriendReqDTO {

	private Integer senderId;
	private Integer receiverId;
	private String memberName;
}
