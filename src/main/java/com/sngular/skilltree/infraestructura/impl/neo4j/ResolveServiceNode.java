package com.sngular.skilltree.infraestructura.impl.neo4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.AssignedRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ClientNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillsCandidateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SubSkillsRelationship;
import com.sngular.skilltree.model.Assignment;
import com.sngular.skilltree.model.Assignments;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.SkillsCandidate;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Named("resolveServiceNode")
public class ResolveServiceNode {

    private final SkillCrudRepository skillCrudRepository;

    private final PositionCrudRepository positionCrudRepository;

    private final ClientCrudRepository clientCrudRepository;

    private final ProjectCrudRepository projectCrudRepository;

    private final PeopleCrudRepository peopleCrudRepository;

    @Named("resolveId")
    public String resolveId(final String id) {
        return id;
    }

    @Named("resolveCodeToSkillNode")
    public SkillNode resolveCodeToSkillNode(final String code) {
        return skillCrudRepository.findSkillByCode(code);
    }

    @Named("mapToAssignment")
    public List<Assignments> mapToAssignment(List<AssignedRelationship> assignedRelationshipList) {
        final List<Assignments> assignmentsList = new ArrayList<>();
        if (Objects.nonNull(assignedRelationshipList)) {
            var assignmentMap = new HashMap<String, List<Assignment>>();
            for (var assignRelationship : assignedRelationshipList) {
                var positionNode = positionCrudRepository.findByCode(assignRelationship.positionNode().getCode());
                assignmentMap.compute(positionNode.getName(), (code, assignsList) -> {
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
                var positionList = positionCrudRepository.findByName(assignment.name());
                for (var assign : assignment.assignments()) {
                    AssignedRelationship assignedRelationship = AssignedRelationship.builder()
                            .assignDate(assign.assignDate())
                                                                                    .positionNode(positionList.get(0))
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
    public Map<String, Skill> mapToSubskill(List<SubSkillsRelationship> subSkillsRelationships) {
        final Map<String, Skill> skillList = new HashMap<>();
        subSkillsRelationships
                .forEach(subSkillsRelationship ->
                        skillList.put(subSkillsRelationship.skillNode().getName(), Skill.builder()
                                .name(subSkillsRelationship.skillNode().getName())
                                .code(subSkillsRelationship.skillNode().getCode())
                                .subSkillList(mapToSubskill(subSkillsRelationship.skillNode().getSubSkills()))
                                .build())
                );
        return skillList;
    }

    @Named("mapToSkillRelationship")
    public List<SubSkillsRelationship> mapToSkillRelationship(Map<String, Skill> skills) {
        final List<SubSkillsRelationship> subSkillsRelationshipsList = new ArrayList<>();
        for (var skill : skills.values()) {
            var skillNode = skillCrudRepository.findSkillByCode(skill.getCode());
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

    @Named("mapToManagedByPeople")
    public People mapToManagedByPeople(String managedBy){
        if (managedBy!=null) {
            var peopleNode = peopleCrudRepository.findByEmployeeId(managedBy);
            return People.builder()
                    .employeeId(peopleNode.getEmployeeId())
                    .name(peopleNode.getName())
                    .surname(peopleNode.getSurname())
                    .title(peopleNode.getTitle())
                    .code(peopleNode.getCode())
                    .assignable(peopleNode.isAssignable())
                    .deleted(peopleNode.isDeleted())
                    .build();
        } else {
            return null;
        }
    }
}
