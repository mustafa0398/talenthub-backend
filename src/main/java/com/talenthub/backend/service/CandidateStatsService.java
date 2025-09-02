package com.talenthub.backend.service;

import com.talenthub.backend.dto.CandidateStatsResponse;
import com.talenthub.backend.model.Candidate;
import com.talenthub.backend.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidateStatsService {
    private final CandidateRepository repo;

    public CandidateStatsService(CandidateRepository repo) {
        this.repo = repo;
    }

    public CandidateStatsResponse getStats() {
        List<Candidate> all = repo.findAll();
        long total = all.size();
        double avgExp = total > 0
            ? all.stream().mapToInt(Candidate::getExperienceYears).average().orElse(0)
            : 0;

        Map<String, Long> byStatus = all.stream()
            .collect(Collectors.groupingBy(c -> c.getPipelineStage().name(), Collectors.counting()));

        Map<String, Long> skillMap = new HashMap<>();
        for (Candidate c : all) {
            if (c.getSkills() != null) {
                for (String s : c.getSkills()) {
                    skillMap.put(s, skillMap.getOrDefault(s, 0L) + 1);
                }
            }
        }
        List<CandidateStatsResponse.SkillCount> topSkills = skillMap.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(12)
            .map(e -> new CandidateStatsResponse.SkillCount(e.getKey(), e.getValue()))
            .toList();

        Map<String, List<Candidate>> byLocationGroups = all.stream()
            .collect(Collectors.groupingBy(Candidate::getLocation));

        List<CandidateStatsResponse.LocationStats> byLocation = byLocationGroups.entrySet().stream()
            .map(e -> {
                var list = e.getValue();
                int min = list.stream().mapToInt(Candidate::getExperienceYears).min().orElse(0);
                int max = list.stream().mapToInt(Candidate::getExperienceYears).max().orElse(0);
                double avg = list.stream().mapToInt(Candidate::getExperienceYears).average().orElse(0);
                return new CandidateStatsResponse.LocationStats(e.getKey(), list.size(), avg, min, max);
            })
            .sorted((a, b) -> Long.compare(b.count(), a.count()))
            .limit(10)
            .toList();

        return new CandidateStatsResponse(total, avgExp, byStatus, topSkills, byLocation);
    }
}
