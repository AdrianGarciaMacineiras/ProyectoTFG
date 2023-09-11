package com.sngular.skilltree.model.views;

import java.time.LocalDate;
import java.util.List;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.EnumMode;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.PositionAssignment;
import com.sngular.skilltree.model.PositionSkill;
import lombok.Builder;

@Builder(toBuilder = true)
public record PositionView(String code, String name, String projectCode, String projectName, LocalDate openingDate, String priority,
                          EnumMode mode, String role, List<PositionSkill> skills,
                          List<Candidate> candidates, List<PositionAssignment> assignedPeople, People managedBy) {
}
