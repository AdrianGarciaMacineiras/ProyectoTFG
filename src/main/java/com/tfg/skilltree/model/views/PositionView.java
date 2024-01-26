package com.tfg.skilltree.model.views;

import java.time.LocalDate;
import java.util.List;
import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.model.EnumMode;
import com.tfg.skilltree.model.People;
import com.tfg.skilltree.model.PositionAssignment;
import com.tfg.skilltree.model.PositionSkill;
import lombok.Builder;

@Builder(toBuilder = true)
public record PositionView(String code, String name, String projectCode, String projectName, LocalDate openingDate, String priority,
                          EnumMode mode, String role, List<PositionSkill> skills,
                          List<Candidate> candidates, List<PositionAssignment> assignedPeople, People managedBy) {
}
