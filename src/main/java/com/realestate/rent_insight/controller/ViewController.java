package com.realestate.rent_insight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }


    @GetMapping("/members/joinComplete")
    public String joinComplete() {
        return "members/joinComplete"; // templates/joinComplete.html
    }
}
