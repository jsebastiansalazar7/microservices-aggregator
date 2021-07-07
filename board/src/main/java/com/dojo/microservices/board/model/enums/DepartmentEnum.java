package com.dojo.microservices.board.model.enums;

public enum DepartmentEnum {
    ACCOUNTING("/results/costs"),
    ENGINEERING("/results/costs"),
    FOOD("/results/costs"),
    PEOPLE("/results/costs"),
    ROOMS("/results/costs"),
    SECURITY("/results/costs"),
    WELCOME("/results/costs");

    private String pathCostsResult;

    DepartmentEnum(String pathCostsResult) {
        this.pathCostsResult = pathCostsResult;
    }

    public String getPathCostsResult() {
        return pathCostsResult;
    }
}
