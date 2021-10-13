package com.raifaizen.storage.controllers;

import com.raifaizen.storage.models.Sock;
import com.raifaizen.storage.service.SockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socks")
public class SockController {

    @Autowired
    private SockService sockService;

    @GetMapping
    public ResponseEntity<List<Sock>> getSocks(
            @RequestParam(required = false, defaultValue = "") String color,
            @RequestParam(required = false, defaultValue = "equal") String operation,
            @RequestParam(required = false, defaultValue = "-1") int cottonPart) {

        List<Sock> socks;

        try {
            socks = sockService.getSocks(color, operation, cottonPart);
        } catch (IllegalArgumentException e) {
            //"There is no such operation"
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            //"Something went wrong"
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(socks, HttpStatus.OK);
    }

    @PostMapping("/income")
    public ResponseEntity<String> incomeSocks(
            @RequestParam String color,
            @RequestParam int cottonPart,
            @RequestParam int quantity) {
        try {
            sockService.income(color, cottonPart, quantity);
        } catch (Throwable e) {
            return new ResponseEntity("cottonPart 0-100", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/outcome")
    public ResponseEntity<String> outcomeSocks(
            @RequestParam String color,
            @RequestParam int cottonPart,
            @RequestParam int quantity) {

        try {
            sockService.outcome(color, cottonPart, quantity);
        } catch (RuntimeException e) {
            return new ResponseEntity("These socks are not in stock", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
