package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.*;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.model.Assignment;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Named("resolveServiceNode")
public class ResolveServiceNode {

    private final SkillCrudRepository skillCrudRepository;

    private final PositionCrudRepository positionCrudRepository;

    @Named("resolveCodeToSkillNode")
    public SkillNode resolveCodeToSkillNode(final String code) {
        var skillNode = skillCrudRepository.findSkillByCode(code);
        skillCrudRepository.deleteRequire(code);
        return skillNode;
    }

    @Named("mapToAssignment")
    public List<Assignments> mapToAssignment(List<AssignedRelationship> assignedRelationshipList) {
        final List<Assignments> assignmentsList = new ArrayList<>();
        var assignmentMap = new HashMap<String, List<Assignment>>();
        for (var assignRelationship : assignedRelationshipList) {
            var positionNode = positionCrudRepository.findByCode(assignRelationship.positionNode().getCode());
            assignmentMap.compute(positionNode.getProject().getName(), (code, assignsList) -> {
                var assign = Assignment.builder()
                            .id(assignRelationship.id())
                            .role(assignRelationship.role())
                            .initDate(assignRelationship.initDate())
                            .endDate(assignRelationship.endDate())
                            .build();
                if (Objects.isNull(assignsList)) {
                    assignsList = new ArrayList<>();
                }
                assignsList.add(assign);
                return assignsList;
            });
        }
        assignmentMap.forEach((code, roleList) -> assignmentsList.add(Assignments.builder().name(code).assignments(roleList).build()));
        return assignmentsList;
    }

    @Named("mapToAssignedRelationship")
    public List<AssignedRelationship> mapToAssignedRelationship(List<Assignments> assignmentsList) {
        final List<AssignedRelationship> assignedRelationshipList = new ArrayList<>();
        if(!Objects.isNull(assignmentsList)) {
            for (var assignment : assignmentsList) {
                var position = positionCrudRepository.findPositionByProject(assignment.name());
                for (var assign : assignment.assignments()) {
                    AssignedRelationship assignedRelationship = new AssignedRelationship(assign.id(), position, assign.endDate(), assign.initDate(), assign.assignDate(), assign.role());
                    assignedRelationshipList.add(assignedRelationship);
                }
            }
        }
        return assignedRelationshipList;
    }

    @Named("mapToSubskill")
    public List<Skill> mapToSubskill(List<SubskillsRelationship> subskillsRelationships) {
        final List<Skill> skillList = new ArrayList<>();
        return skillList;
    }

    @Named("mapToSkillRelationship")
    public List<SubskillsRelationship> mapToSkillRelationship(List<Skill> skills) {
        final List<SubskillsRelationship> subskillsRelationshipsList = new ArrayList<>();
        for (var skill : skills) {
            var skillNode = skillCrudRepository.findSkillByCode(skill.code());
            SubskillsRelationship subskillsRelationship = new SubskillsRelationship(null, skillNode, "own");
            subskillsRelationshipsList.add(subskillsRelationship);
        }
        return subskillsRelationshipsList;
    }

    @Named("mapToSkillCandidate")
    public List<SkillsCandidate> mapToSkillCandidate(List<SkillsCandidateRelationship> skillsCandidateRelationshipList) {
        final List<SkillsCandidate> skillsCandidateList = new ArrayList<>();
        for (var skillCandidateRelationship : skillsCandidateRelationshipList) {
            var skill = SkillsCandidate.builder()
                    .code(skillCandidateRelationship.skill().getCode())
                    .experience(skillCandidateRelationship.experience())
                    .level(skillCandidateRelationship.level()).build();
            skillsCandidateList.add(skill);
        }
        return skillsCandidateList;
    }

    @Named("mapToSkillCandidateRelationship")
    public List<SkillsCandidateRelationship> mapToSkillCandidateRelationship(List<SkillsCandidate> skillsCandidateList) {
        final List<SkillsCandidateRelationship> skillsCandidateRelationshipList = new ArrayList<>();
        for (var skill : skillsCandidateList) {
            var skillNode = skillCrudRepository.findSkillByCode(skill.code());
            SkillsCandidateRelationship skillsCandidateRelationship = new SkillsCandidateRelationship(null, skillNode,
                    skill.level(), skill.experience());
            skillsCandidateRelationshipList.add(skillsCandidateRelationship);
        }
        return skillsCandidateRelationshipList;
    }

}
