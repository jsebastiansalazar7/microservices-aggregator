package com.dojo.microservices.people.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;

@Slf4j
@RequestMapping("/results")
@RestController
public class ResultsController {

    @GetMapping("/costs")
    public ResponseEntity<Double> getTotalCosts() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        int high = 1500;
        int low = 500;
        String costs = decimalFormat.format(Math.random() * (high - low) + low);
        return ResponseEntity.ok().body(Double.valueOf(costs));
    }

}
