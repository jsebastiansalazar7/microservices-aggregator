package com.dojo.microservices.board.controller;

import com.dojo.microservices.board.response.ReportCostsResponse;
import com.dojo.microservices.board.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/costs")
    public ResponseEntity<ReportCostsResponse> getTotalCosts() {
        return ResponseEntity.ok().body(reportService.getCostsReport());
    }

}
