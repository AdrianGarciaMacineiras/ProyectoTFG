package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.*;
import com.sngular.skilltree.model.Assignment;
import com.sngular.skilltree.model.Assignments;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.SkillsCandidate;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Named("resolveServiceNode")
public class ResolveServiceNode {

    private final SkillCrudRepository skillCrudRepository;

    private final PositionCrudRepository positionCrudRepository;

    private final ClientCrudRepository clientCrudRepository;

    private final ProjectCrudRepository projectCrudRepository;

    @Named("resolveId")
    public String resolveId(final String id) {
        return id;
    }

    @Named("resolveCodeToSkillNode")
    public SkillNode resolveCodeToSkillNode(final String code) {
        var skillNode = skillCrudRepository.findSkillByCode(code);
        skillCrudRepository.deleteRequire(code);
        return skillNode;
    }

    @Named("mapToAssignment")
    public List<Assignments> mapToAssignment(List<AssignedRelationship> assignedRelationshipList) {
        final List<Assignments> assignmentsList = new ArrayList<>();
        if (Objects.nonNull(assignedRelationshipList)) {
            var assignmentMap = new HashMap<String, List<Assignment>>();
            for (var assignRelationship : assignedRelationshipList) {
                var positionNode = positionCrudRepository.findByCode(assignRelationship.positionNode().getCode());
                assignmentMap.compute(positionNode.getProject().getName(), (code, assignsList) -> {
                    var assign = Assignment.builder()
                            .id(assignRelationship.id())
                            .role(assignRelationship.role())
                            .initDate(assignRelationship.initDate())
                            .endDate(assignRelationship.endDate())
                            .assignDate(assignRelationship.assignDate())
                            .dedication(assignRelationship.dedication())
                            .build();
                    if (Objects.isNull(assignsList)) {
                        assignsList = new ArrayList<>();
                    }
                    assignsList.add(assign);
                    return assignsList;
                });
            }
            assignmentMap.forEach((code, roleList) -> assignmentsList.add(Assignments.builder().name(code).assignments(roleList).build()));
        }
        return assignmentsList;
    }

    @Named("mapToAssignedRelationship")
    public List<AssignedRelationship> mapToAssignedRelationship(List<Assignments> assignmentsList) {
        final List<AssignedRelationship> assignedRelationshipList = new ArrayList<>();
        if (!Objects.isNull(assignmentsList)) {
            for (var assignment : assignmentsList) {
                var position = positionCrudRepository.findPositionByProject(assignment.name());
                for (var assign : assignment.assignments()) {
                    AssignedRelationship assignedRelationship = AssignedRelationship.builder()
                            .assignDate(assign.assignDate())
                            .positionNode(position)
                            .endDate(assign.endDate())
                            .initDate(assign.initDate())
                            .id(assign.id())
                            .role(assign.role())
                            .build();
                    assignedRelationshipList.add(assignedRelationship);
                }
            }
        }
        return assignedRelationshipList;
    }

    @Named("mapToSubskill")
    public List<Skill> mapToSubskill(List<SubSkillsRelationship> subSkillsRelationships) {
        final List<Skill> skillList = new ArrayList<>();
        subSkillsRelationships
                .forEach(subSkillsRelationship ->
                        skillList.add(Skill.builder()
                                .name(subSkillsRelationship.skillNode().getName())
                                .code(subSkillsRelationship.skillNode().getCode())
                                .subSkills(mapToSubskill(subSkillsRelationship.skillNode().getSubSkills()))
                                .build())
                );
        return skillList;
    }

    @Named("mapToSkillRelationship")
    public List<SubSkillsRelationship> mapToSkillRelationship(List<Skill> skills) {
        final List<SubSkillsRelationship> subSkillsRelationshipsList = new ArrayList<>();
        for (var skill : skills) {
            var skillNode = skillCrudRepository.findSkillByCode(skill.code());
            SubSkillsRelationship subskillsRelationship = new SubSkillsRelationship(null, skillNode, "own");
            subSkillsRelationshipsList.add(subskillsRelationship);
        }
        return subSkillsRelationshipsList;
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

    @Named("mapToClientString")
    public List<String> mapToClientString(List<ClientNode> clientNodeList) {
        final List<String> clientNameList = new ArrayList<>();
        for (var clientNode : clientNodeList) {
            clientNameList.add(clientNode.getName());
        }
        return clientNameList;
    }

    @Named("mapToClientNode")
    public List<ClientNode> mapToClientNode(List<String> clientNameList) {
        final List<ClientNode> clientNodeList = new ArrayList<>();
        for (var name : clientNameList) {
            var clientNode = clientCrudRepository.findByName(name);
            clientNodeList.add(clientNode);
        }
        return clientNodeList;
    }

    @Named("mapToProjectNode")
    public List<ProjectNode> mapToProjectNode(List<String> projectNameList) {
        final List<ProjectNode> projectNodeList = new ArrayList<>();
        for (var name : projectNameList) {
            var projectNode = projectCrudRepository.findByName(name);
            projectNodeList.add(projectNode);
        }
        return projectNodeList;
    }

    @Named("mapToProjectString")
    public List<String> mapToProjectString(List<ProjectNode> projectNodeList) {
        final List<String> projectNameList = new ArrayList<>();
        for (var projectNode : projectNodeList) {
            projectNameList.add(projectNode.getName());
        }
        return projectNameList;
    }
}
