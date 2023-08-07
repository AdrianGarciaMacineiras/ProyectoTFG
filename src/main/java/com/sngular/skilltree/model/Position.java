package com.sngular.skilltree.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record Position(String code, String name, Project project, LocalDate openingDate, LocalDate closingDate,
                       String priority, EnumMode mode, Office office, String role, List<PositionSkill> skills,
                       List<Candidate> candidates, List<PositionAssignment> assignedPeople, People managedBy,
                       boolean deleted, String active) {
}
