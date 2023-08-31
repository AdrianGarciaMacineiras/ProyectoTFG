package com.sngular.skilltree.model.views;

import com.sngular.skilltree.model.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record PositionView(String code, String name, String projectCode, LocalDate openingDate, String priority,
                          EnumMode mode, String role, List<PositionSkill> skills,
                          List<Candidate> candidates, List<PositionAssignment> assignedPeople, People managedBy) {
}
