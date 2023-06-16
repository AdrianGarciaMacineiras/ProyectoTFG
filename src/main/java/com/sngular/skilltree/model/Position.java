package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;


@Builder(toBuilder = true)
public record Position(String code, String name, Project project, LocalDate openingDate, LocalDate closingDate,
                       String priority, EnumMode mode, Office office, String role, List<PositionSkill> skills,
                       List<Candidate> candidates, List<PositionAssignment> assignedPeople, People managedBy,
                       boolean deleted) {

}
