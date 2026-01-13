package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.service.StatisticsService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public Map<String, Object> getStatistics() {
        return statisticsService.generateStatistics();
    }

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> exportStatistics(@RequestParam String format) {

        ByteArrayResource file = statisticsService.exportStatistics(format);

        String contentType = "application/pdf";
        if ("xlsx".equalsIgnoreCase(format)) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=statistics." + format)
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }
}
