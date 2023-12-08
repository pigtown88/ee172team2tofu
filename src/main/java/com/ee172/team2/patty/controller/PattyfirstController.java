package com.ee172.team2.patty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PattyfirstController {

    @GetMapping("/friend_select2")
    public String friendSelect2(){
        return "/patty/friend/friend_select";

    }
    
    @GetMapping("/showArenas")
    public String Arenas(){
        return "/patty/arena/show_arena";

    }
    
    @GetMapping("/showActivity")
    public String Activity(){
        return "/patty/activity/show_activity";

    }
}
