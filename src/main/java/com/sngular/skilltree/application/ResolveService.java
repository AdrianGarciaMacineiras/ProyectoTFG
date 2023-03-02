package com.sngular.skilltree.application;

import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OfficeNodeMapper;
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

    private final OpportunityService opportunityService;

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
        return skillService.findByCode(code);
    }

    @Named("resolveCodeSkillList")
    public List<Skill> resolveCodeSkill(final List<String> codeList) {
        final var skillList = new ArrayList<Skill>();
        if (codeList != null) {
            for (var code : codeList) {
                skillList.add(resolveCodeSkill(code));
            }
        }
        return skillList;
    }

    @Named("resolveCodeOpportunity")
    public Opportunity resolveCodeOpportunity(final String code) {
        return opportunityService.findByCode(code);
    }

    @Named("resolveCodeOpportunityList")
    public List<Opportunity> resolveCodeOpportunityList(final List<String> codeList) {
        final var opportunityList = new ArrayList<Opportunity>();
        if (codeList != null) {
            for (var code : codeList) {
                opportunityList.add(resolveCodeOpportunity(code));
            }
        }
        return opportunityList;
    }

    @Named("resolveCodePeople")
    public People resolveCodePeople(final String peopleCode) {
        return peopleService.findByCode(peopleCode);
    }

    @Named("resolveCodeProject")
    public Project resolveCodeProject(final String projectCode) {return projectService.findByCode(projectCode);}

    @Named("resolveCodeOffice")
    public Office resolveCodeOffice(final String officeCode) {return officeService.findByCode(officeCode);}

    @Named("resolveCodeClient")
    public Client resolveCodeClient(final String clientCode){return clientService.findByCode(clientCode);}

    /*@Named("updateClient")
    public Client updateClient(Client newClient, Client oldClient){
        oldClient.toBuilder()
                .industry(newClient.industry())
                .country(newClient.country())
                .HQ(newClient.HQ())
                .offices(newClient.offices())
                .principalOffice(newClient.principalOffice())
                .name(newClient.name())
                .build();
        return oldClient;
    }*/


}
