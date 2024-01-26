package com.tfg.skilltree.infraestructura.impl.neo4j.querymodel;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record PositionExtendedView(String code, String name, String projectCode, String projectName, LocalDateTime openingDate, String priority,
                                   String mode, String role, List<CandidateView> candidates, String managedBy) {

    @Builder
    public record CandidateView(String code, String status, LocalDateTime introductionDate, LocalDateTime resolutionDate,
                               LocalDateTime creationDate, LocalDateTime interviewDate, String candidateCode){}

}
