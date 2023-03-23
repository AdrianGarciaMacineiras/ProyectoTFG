package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.*;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.model.Roles;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Named("mapToParticipate")
    public List<Participate> mapToParticipate(List<ParticipateRelationship> participateRelationshipList) {
        final List<Participate> participateList = new ArrayList<>();
        var participateMap = new HashMap<String, List<Roles>>();
        for (var participateRelationship : participateRelationshipList) {
            participateMap.compute(participateRelationship.project().getName(), (code, roleList) -> {
                var rol = Roles.builder()
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
        participateMap.forEach((code, roleList) -> participateList.add(Participate.builder().name(code).roles(roleList).build()));
        return participateList;
    }

    @Named("mapToParticipateRelationship")
    public List<ParticipateRelationship> mapToParticipateRelationship(List<Participate> participateList) {
        final List<ParticipateRelationship> participateRelationshipList = new ArrayList<>();
        for (var participate : participateList) {
            LocalDate endDate = null;
            LocalDate initDate = null;
            String role = null;
            var project = projectCrudRepository.findByName(participate.name());
            for (var rol : participate.roles()) {
                endDate = rol.endDate();
                initDate = rol.initDate();
                role = rol.role();
                ParticipateRelationship participateRelationship = new ParticipateRelationship(null, project, endDate, initDate, role);
                participateRelationshipList.add(participateRelationship);
            }
        }
        return participateRelationshipList;
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
