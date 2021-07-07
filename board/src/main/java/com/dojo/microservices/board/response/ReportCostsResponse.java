package com.dojo.microservices.board.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReportCostsResponse {
    private Double accounting;
    private Double engineering;
    private Double food;
    private Double people;
    private Double rooms;
    private Double security;
    private Double welcome;
    private Double board;
    private Double totalCosts;
}
