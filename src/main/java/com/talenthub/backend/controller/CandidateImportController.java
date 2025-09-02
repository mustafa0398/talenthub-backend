package com.talenthub.backend.controller;

import com.talenthub.backend.model.Candidate;
import com.talenthub.backend.repository.CandidateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateImportController {
    private final CandidateRepository repo;

    public CandidateImportController(CandidateRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/import")
    public List<Candidate> importCandidates(@RequestBody List<Candidate> candidates) {
        return repo.saveAll(candidates);
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        repo.deleteAll();
    }
}
