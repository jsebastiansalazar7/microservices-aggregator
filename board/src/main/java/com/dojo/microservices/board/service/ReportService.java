package com.dojo.microservices.board.service;

import com.dojo.microservices.board.client.ResultsClient;
import com.dojo.microservices.board.model.enums.DepartmentEnum;
import com.dojo.microservices.board.response.ReportCostsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ReportService {

    private ResultsClient resultsClient;

    @Autowired
    public ReportService(ResultsClient resultsClient) {
        this.resultsClient = resultsClient;
    }

    public ReportCostsResponse getCostsReport() {
        Double accountingCosts = getCostsResult(DepartmentEnum.ACCOUNTING);
        Double engineeringCosts = getCostsResult(DepartmentEnum.ENGINEERING);
        Double foodCosts = getCostsResult(DepartmentEnum.FOOD);
        Double peopleCosts = getCostsResult(DepartmentEnum.PEOPLE);
        Double roomsCosts = getCostsResult(DepartmentEnum.ROOMS);
        Double securityCosts = getCostsResult(DepartmentEnum.SECURITY);
        Double welcomeCosts = getCostsResult(DepartmentEnum.WELCOME);
        Double boardCosts = getCostsForBoard();

        List<Double> costs = Arrays.asList(accountingCosts, engineeringCosts, foodCosts, peopleCosts,
                roomsCosts, securityCosts, welcomeCosts, boardCosts);

        Double totalCosts = getTotalCosts(costs);

        return ReportCostsResponse.builder()
                .accounting(accountingCosts)
                .engineering(engineeringCosts)
                .food(foodCosts)
                .people(peopleCosts)
                .rooms(roomsCosts)
                .security(securityCosts)
                .welcome(welcomeCosts)
                .board(boardCosts)
                .totalCosts(totalCosts)
                .build();
    }

    private Double getCostsResult(DepartmentEnum department) {
        Optional<Double> costsResult = resultsClient.requestCostsResultForDepartment(department);
        if (costsResult.isPresent()) {
            return costsResult.get();
        } else {
            return null;
        }
    }

    private Double getCostsForBoard() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        int high = 3500;
        int low = 2000;
        String costs = decimalFormat.format(Math.random() * (high - low) + low);
        return Double.valueOf(costs);
    }

    private Double getTotalCosts(List<Double> costs) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double totalCost = costs.stream()
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
        return Double.valueOf(decimalFormat.format(totalCost));
    }
}
