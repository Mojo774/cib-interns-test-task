package com.raifaizen.storage.controllers;

import com.raifaizen.storage.models.Sock;
import com.raifaizen.storage.service.SockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
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
            return ResponseEntity.badRequest()
                    .header("operation","There is no such operation")
                    .build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .header("error","Something went wrong")
                    .build();
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
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest()
                    .header("error","Validation")
                    .build();
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
            return ResponseEntity.badRequest()
                    .header("error","These socks are not in stock")
                    .build();
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
