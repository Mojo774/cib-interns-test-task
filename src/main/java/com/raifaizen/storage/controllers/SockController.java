package com.raifaizen.storage.controllers;

import com.raifaizen.storage.models.Sock;
import com.raifaizen.storage.service.SockService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/socks")
public class SockController {

    @Autowired
    private SockService sockService;

    @GetMapping
    public String getSocks(
            @RequestParam(required = false, defaultValue = "") String color,
            @RequestParam(required = false, defaultValue = "equal") String operation,
            @RequestParam(required = false, defaultValue = "-1") int cottonPart,
            Model model) {

        List<Sock> socks = new ArrayList<>();

        try {
            socks = sockService.getSocks(color, operation, cottonPart);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "There is no such operation");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Something went wrong");
        }

        model.addAttribute("socks", socks);

        return "main";
    }

    @PostMapping("/income")
    public String incomeSocks(
            @RequestParam String color,
            @RequestParam int cottonPart,
            @RequestParam int quantity
    ) {

        sockService.income(color, cottonPart, quantity);

        return "redirect:/socks";
    }

    @PostMapping("/outcome")
    public String outcomeSocks(
            @RequestParam String color,
            @RequestParam int cottonPart,
            @RequestParam int quantity,
            Model model
    ) {

        try {
            sockService.outcome(color, cottonPart, quantity);
        } catch (RuntimeException e) {
            model.addAttribute("error", "There are no such socks");
        }

        return "redirect:/socks";
    }

}
