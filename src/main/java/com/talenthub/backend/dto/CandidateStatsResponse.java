package com.talenthub.backend.dto;

import java.util.List;
import java.util.Map;

public record CandidateStatsResponse(
    long total,
    double avgExperience,
    Map<String, Long> byStatus,
    List<SkillCount> topSkills,
    List<LocationStats> byLocation
) {
    public record SkillCount(String skill, long count) {}
    public record LocationStats(
        String location,
        long count,
        double avgExp,
        int minExp,
        int maxExp
    ) {}
}
