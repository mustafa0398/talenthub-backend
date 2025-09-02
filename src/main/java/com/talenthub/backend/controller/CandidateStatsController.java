package com.talenthub.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talenthub.backend.dto.CandidateStatsResponse;
import com.talenthub.backend.service.CandidateStatsService;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // oder "*" für alle
public class CandidateStatsController {
    private final CandidateStatsService statsService;

    public CandidateStatsController(CandidateStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/api/candidates/stats")
    public CandidateStatsResponse getStats() {
        return statsService.getStats();
    }
}
