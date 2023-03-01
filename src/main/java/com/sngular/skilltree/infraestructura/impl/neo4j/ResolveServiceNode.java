package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.*;
import com.sngular.skilltree.model.Participate;
import com.sngular.skilltree.model.Roles;
import com.sngular.skilltree.model.Skill;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Named("resolveServiceNode")
public class ResolveServiceNode {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    private final OfficeCrudRepository officeCrudRepository;

    private final PeopleCrudRepository peopleCrudRepository;

    private final SkillCrudRepository skillCrudRepository;

    private final ProjectCrudRepository projectCrudRepository;

    @Named("resolveCodeToOfficeNode")
    public OfficeNode resolveCodeOfficeNode(final String code) {
        return officeCrudRepository.findByCode(code);
    }

    @Named("resolveCodeOfficeNodeList")
    public List<OfficeNode> resolveCodeOfficeNode(final List<String> codeList) {
        final var officeNodeList = new ArrayList<OfficeNode>();
        if (codeList != null) {
            for (var code : codeList) {
                officeNodeList.add(resolveCodeOfficeNode(code));
            }
        }
        return officeNodeList;
    }

    @Named("resolveCodeToPeopleNode")
    public PeopleNode resolveCodePeopleNode(final String code) {
        return peopleCrudRepository.findByCode(code);
    }

    @Named("resolveCodeToSkillNode")
    public SkillNode resolveCodeToSkillNode(final String code) {return skillCrudRepository.findByCode(code);}

    @Named("mapToParticipate")
    public List<Participate> mapToParticipate(List<ParticipateRelationship> participateRelationshipList) {
        final List<Participate> participateList = new ArrayList<>();
        var participateMap = new HashMap<String, List<Roles>>();
        for (var participateRelationship : participateRelationshipList) {
            participateMap.compute(participateRelationship.project().getCode(), (code, roleList) -> {
                try {
                    var rol = Roles.builder()
                            .role(participateRelationship.role())
                            .initDate(formatter.parse(participateRelationship.initDate()))
                            .endDate(formatter.parse(participateRelationship.endDate()))
                            .build();
                    if (Objects.isNull(roleList)) {
                        roleList = new ArrayList<>();
                    }
                    roleList.add(rol);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                return roleList;
            });
        }
        participateMap.forEach((code, roleList) -> participateList.add(Participate.builder().code(code).roles(roleList).build()));
        return participateList;
    }

    @Named("mapToParticipateRelationship")
    public List<ParticipateRelationship> mapToParticipateRelationship(List<Participate> participateList){
        final List<ParticipateRelationship> participateRelationshipList = new ArrayList<>();
        for(var participate: participateList){
            String endDate = null;
            String initDate = null;
            String role = null;
            var project = projectCrudRepository.findByCode(participate.code());
            for(var rol: participate.roles()){
                endDate = formatter.format(rol.endDate());
                initDate = formatter.format(rol.initDate());
                role = rol.role();
            }
            ParticipateRelationship participateRelationship = new ParticipateRelationship(null, project, endDate, initDate, role);
            participateRelationshipList.add(participateRelationship);
        }
        return participateRelationshipList;
    }

    @Named("mapToSubskill")
    public List<Skill> mapToSubskill(List<SubskillsRelationship> subskillsRelationships){
        final List<Skill> skillList = new ArrayList<>();
        /*for (var subSkill: subskillsRelationships){
            var skill = Skill.builder()
                    .name(subSkill.skillNode().getName())
                    .code(subSkill.skillNode().getCode())
                    .subSkills(new ArrayList<>())
                    .build();
            skillList.add(skill);
        }*/
        return skillList;
    }

    @Named("mapToSkillRelationship")
    public List<SubskillsRelationship> mapToSkillRelationship(List<Skill> skills){
        final List<SubskillsRelationship> subskillsRelationshipsList = new ArrayList<>();
        for (var skill: skills){
            var skillNode = skillCrudRepository.findByCode(skill.code());
            SubskillsRelationship subskillsRelationship = new SubskillsRelationship(null, skillNode,"own");
            subskillsRelationshipsList.add(subskillsRelationship);
        }
        return subskillsRelationshipsList;
    }

}
