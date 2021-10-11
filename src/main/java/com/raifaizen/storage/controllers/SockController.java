package com.raifaizen.storage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/socks")
public class SockController {

    @GetMapping
    public String getSocks(
            @RequestParam(required = false, defaultValue = "") String color,
            @RequestParam(required = false, defaultValue = "") String operation,
            @RequestParam(required = false, defaultValue = "") int cottonPart,
            Model model) {
        System.out.println("ss");
        return "main";
    }

    @GetMapping("/1")
    public String getSocks(

            Model model) {
        System.out.println("ss2");
        return "main";
    }
}
