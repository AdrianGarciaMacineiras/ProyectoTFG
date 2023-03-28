package com.sngular.skilltree.application;

import com.sngular.skilltree.model.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Named("resolveService")
public class ResolveService {

    private final SkillService skillService;

    private final PuestoService puestoService;

    private final PeopleService peopleService;

    private final ProjectService projectService;

    private final OfficeService officeService;

    private final ClientService clientService;

    @Named("resolveSkillCode")
    public String resolveSkillToCode(final Skill skill) {
        return skill.name();
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

    @Named("resolveCodePuesto")
    public Puesto resolveCodePuesto(final String code) {
        return puestoService.findByCode(code);
    }

    @Named("resolveCodePuestoList")
    public List<Puesto> resolveCodePuestoList(final List<String> codeList) {
        final var opportunityList = new ArrayList<Puesto>();
        if (codeList != null) {
            for (var code : codeList) {
                opportunityList.add(resolveCodePuesto(code));
            }
        }
        return opportunityList;
    }

    @Named("resolveCodePeople")
    public People resolveCodePeople(final Long peopleCode) {
        return peopleService.findPeopleByCode(peopleCode);
    }

    @Named("resolveCodeProject")
    public Project resolveCodeProject(final Long projectCode) {return projectService.findProject(projectCode);}

    @Named("resolveCodeOffice")
    public Office resolveCodeOffice(final String officeCode) {return officeService.findByCode(officeCode);}

    @Named("resolveCodeClient")
    public Client resolveCodeClient(final Long clientCode){return clientService.findByCode(clientCode);}

}
