package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PositionExtendedView(String code, String name, String projectCode, LocalDateTime openingDate, String priority,
                                   String mode, String role, List<CandidateView> candidates, String managedBy) {

    @Builder
    public record CandidateView(String code, String status, LocalDateTime introductionDate, LocalDateTime resolutionDate,
                               LocalDateTime creationDate, LocalDateTime interviewDate){}

}
