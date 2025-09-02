package com.talenthub.backend.controller;

import com.talenthub.backend.model.Candidate;
import com.talenthub.backend.repository.CandidateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    private final CandidateRepository repository;

    public CandidateController(CandidateRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Candidate> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Candidate create(@RequestBody Candidate candidate) {
        return repository.save(candidate);
    }

    
    @PutMapping("/{id}")
    public Candidate update(@PathVariable Long id, @RequestBody Candidate candidate) {
        candidate.setId(id);
        return repository.save(candidate);
    }

    @PatchMapping("/{id}")
    public Candidate patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Candidate candidate = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id " + id));

        if (updates.containsKey("name")) {
            candidate.setName((String) updates.get("name"));
        }
        if (updates.containsKey("role")) {
            candidate.setRole((String) updates.get("role"));
        }
        if (updates.containsKey("location")) {
            candidate.setLocation((String) updates.get("location"));
        }
        if (updates.containsKey("experienceYears")) {
            candidate.setExperienceYears((Integer) updates.get("experienceYears"));
        }
        if (updates.containsKey("skills")) {
            @SuppressWarnings("unchecked")
            List<String> skills = (List<String>) updates.get("skills");
            candidate.setSkills(skills);
        }
        if (updates.containsKey("pipelineStage")) {
            String stage = (String) updates.get("pipelineStage");
            candidate.setPipelineStage(Enum.valueOf(
                    com.talenthub.backend.model.PipelineStage.class,
                    stage
            ));
        }

        return repository.save(candidate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
