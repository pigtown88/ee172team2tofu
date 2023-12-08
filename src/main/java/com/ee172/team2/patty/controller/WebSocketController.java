//package com.ee172.team2.patty.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ee172.team2.patty.storage.UserStorage;
//
//@RestController
//public class WebSocketController {
//	
//	@Autowired
//	private SimpMessagingTemplate simpMessagingTemplate;
//
//	@MessageMapping("/chet/{to}")
//	public void sendMessage(@DestinationVariable String to, WebSocketController message) {
//		System.out.println("handling send message: " + message + ":to: " + to);
//		boolean isExists = UserStorage.getInstance().getUsers().contains(to);
//		if(isExists) {
//			simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
//		}
//	}
//}
