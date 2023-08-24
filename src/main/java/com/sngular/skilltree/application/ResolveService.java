package com.sngular.skilltree.application;

import com.sngular.skilltree.api.model.SkillPairDTO;
import com.sngular.skilltree.model.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResolveService {

    private final SkillService skillService;

    private final PositionService positionService;

    private final PeopleService peopleService;

    private final ProjectService projectService;

    private final OfficeService officeService;

    private final ClientService clientService;

    @Named("resolveSkillPair")
    public SkillPairDTO resolveSkillToSkillPair(final Skill skill) {
        return SkillPairDTO.builder()
                .name(skill.getName())
                .code(skill.getCode())
                .build();
    }

    @Named("resolveSkillPairList")
    public List<SkillPairDTO> resolveSkillToSkillPair(final List<Skill> skillList){
        final var nameList = new ArrayList<SkillPairDTO>();
        if (skillList != null) {
            for (var skill : skillList) {
                nameList.add(resolveSkillToSkillPair(skill));
            }
        }
        return nameList;
    }

    @Named("resolveSkillCode")
    public String resolveSkillToCode(final Skill skill) {
        return skill.getName();
    }

    @Named("resolveSkillCodeList")
    public List<String> resolveSkillToCode(final List<Skill> skillList){
        final var codeList = new ArrayList<String>();
        if (skillList != null) {
            for (var skill : skillList) {
                codeList.add(resolveSkillToCode(skill));
            }
        }
        return codeList;
    }

    @Named("resolveCodeToSkill")
    public Skill resolveCodeSkill(final String code) {
        return skillService.findSkill(code);
    }

    @Named("resolveCodeSkillList")
    public List<Skill> resolveCodeSkill(final List<String> codeList) {
        var skillList = new ArrayList<Skill>();
        if (codeList != null) {
            for (var code : codeList) {
                skillList.add(resolveCodeSkill(code));
            }
        } else {
            skillList = null;
        }
        return skillList;
    }

    @Named("resolveNameToSkill")
    public Skill resolveNameSkill(final SkillPairDTO skillPairDTO) {
        return skillService.findByName(skillPairDTO.getName());
    }

    @Named("resolveSkillPairDtoList")
    public List<Skill> resolveNameSkill(final List<SkillPairDTO> skillPairDTOList) {
        var skillList = new ArrayList<Skill>();
        if (skillPairDTOList != null) {
            for (var name : skillPairDTOList) {
                skillList.add(resolveNameSkill(name));
            }
        } else {
            skillList = null;
        }
        return skillList;
    }

    @Named("resolveCodePosition")
    public Position resolveCodePosition(final String code) {
        return positionService.findByCode(code);
    }

    @Named("resolveCodePositionList")
    public List<Position> resolveCodePositionList(final List<String> codeList) {
        final var positionList = new ArrayList<Position>();
        if (codeList != null) {
            for (var code : codeList) {
                positionList.add(resolveCodePosition(code));
            }
        }
        return positionList;
    }

    @Named("resolveCodePeople")
    public People resolveCodePeople(final String peopleCode) {
        return peopleService.findPeopleByCode(peopleCode);
    }

    @Named("resolveCodeProject")
    public Project resolveCodeProject(final String projectCode) {
        return projectService.findProject(projectCode);
    }

    @Named("resolveCodeOffice")
    public Office resolveCodeOffice(final String officeCode) {return officeService.findByCode(officeCode);}

    @Named("resolveCodeClient")
    public Client resolveCodeClient(final String clientCode) {
        return clientService.findByCode(clientCode);
    }

}
