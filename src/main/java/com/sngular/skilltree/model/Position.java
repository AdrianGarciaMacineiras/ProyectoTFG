package com.sngular.skilltree.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Singular;

@Builder(toBuilder = true)
public record Position(String code, String name, Project project, LocalDate openingDate, LocalDate closingDate,
                       String priority, EnumMode mode, Office office, String role, @Singular List<PositionSkill> skills,
                       @Singular List<Candidate> candidates, @Singular("assignEmployee") List<PositionAssignment> assignedPeople, People managedBy,
                       boolean deleted, String active) {
}
