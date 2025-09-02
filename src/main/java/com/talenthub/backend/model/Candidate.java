package com.talenthub.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data                   
@NoArgsConstructor      
@AllArgsConstructor     
@Builder                
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;

    @ElementCollection
    private List<String> skills = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PipelineStage pipelineStage;

    private String location;
    private int experienceYears;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
