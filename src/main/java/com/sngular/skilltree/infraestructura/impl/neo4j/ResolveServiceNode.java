package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.*;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.model.Assigns;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Named("resolveServiceNode")
public class ResolveServiceNode {

    private final OfficeCrudRepository officeCrudRepository;

    private final PeopleCrudRepository peopleCrudRepository;

    private final SkillCrudRepository skillCrudRepository;

    private final ProjectCrudRepository projectCrudRepository;

    @Named("resolveCodeToSkillNode")
    public SkillNode resolveCodeToSkillNode(final String code) {
        var skillNode = skillCrudRepository.findSkillByCode(code);
        skillCrudRepository.deleteRequire(code);
        return skillNode;
    }

    @Named("mapToAssignment")
    public List<Assignments> mapToAssignment(List<AssignedRelationship> assignedRelationshipList) {
        final List<Assignments> assignmentsList = new ArrayList<>();
        var assignmentMap = new HashMap<String, List<Assigns>>();
        for (var assignRelationship : assignedRelationshipList) {
            assignmentMap.compute(participateRelationship.project().getName(), (code, roleList) -> {
                var rol = Assigns.builder()
                            .role(participateRelationship.role())
                            .initDate(participateRelationship.initDate())
                            .endDate(participateRelationship.endDate())
                            .build();
                if (Objects.isNull(roleList)) {
                    roleList = new ArrayList<>();
                }
                roleList.add(rol);
                return roleList;
            });
        }
        participateMap.forEach((code, roleList) -> assignmentsList.add(Assignments.builder().name(code).roles(roleList).build()));
        return assignmentsList;
    }

    @Named("mapToParticipateRelationship")
    public List<AssignedRelationship> mapToParticipateRelationship(List<Assignments> assignmentsList) {
        final List<AssignedRelationship> assignedRelationshipList = new ArrayList<>();
        for (var participate : assignmentsList) {
            LocalDate endDate = null;
            LocalDate initDate = null;
            String role = null;
            var project = projectCrudRepository.findByName(participate.name());
            for (var rol : participate.roles()) {
                endDate = rol.endDate();
                initDate = rol.initDate();
                role = rol.role();
                AssignedRelationship assignedRelationship = new AssignedRelationship(null, project, endDate, initDate, role);
                assignedRelationshipList.add(assignedRelationship);
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
