package com.ee172.team2.patty.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendReqDTO {

	private Integer senderId;
	private Integer receiverId;
	private Integer status;
	
	
}
