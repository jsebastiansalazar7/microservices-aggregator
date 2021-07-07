package com.dojo.microservices.board.client;

import com.dojo.microservices.board.model.enums.DepartmentEnum;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class ResultsClient {

    private final Map<DepartmentEnum, String> reportServiceUrlMap;

    private final WebClient.Builder clientBuilder;

    @Autowired
    public ResultsClient(@Value("#{${result.service.url}}") Map<DepartmentEnum, String> reportServiceUrlMap,
                         WebClient.Builder clientBuilder) {
        this.reportServiceUrlMap = reportServiceUrlMap;
        this.clientBuilder = clientBuilder;
    }

    public Optional<Double> requestCostsResultForDepartment(DepartmentEnum department) {
        String url = reportServiceUrlMap.get(department);
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        Double response = client
                .get()
                .uri(uriBuilder -> uriBuilder.path(department.getPathCostsResult()).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, res -> Mono.empty())
                .bodyToMono(Double.class)
                .doOnError(e -> log.info("Couldn't connect to the service={}", department.name()))
                .onErrorReturn(-1.0)
                .block();

        Optional<Double> costsResult = Optional.empty();
        if (response != null && response != -1.0) {
            costsResult = Optional.of(response);
        } else {
            log.info("The report of {} was null", department.name().toLowerCase());
        }
        return costsResult;
    }


}
