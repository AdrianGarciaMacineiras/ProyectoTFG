package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Skill;
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

    @Named("resolveSkillCode")
    public String resolveSkillToCode(final Skill skill) {
        return skill.name();
    }

    @Named("resolveSkillCodeList")
    public List<String> resolveSkillToCode(final List<Skill> skillList){
        final var codeList = new ArrayList<String>();
        for (var skill : skillList) {
            codeList.add(resolveSkillToCode(skill));
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
        for (var code : codeList) {
            skillList.add(resolveCodeSkill(code));
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
        for (var code : codeList) {
            opportunityList.add(resolveCodeOpportunity(code));
        }
        return opportunityList;
    }

    @Named("resolveCodePeople")
    public People resolveCodePeople(final String peopleCode) {
        return peopleService.findByCode(peopleCode);
    }

}
