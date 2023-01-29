package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Opportunity;
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

    public Skill resolveCodeSkill(final String code) {
        return skillService.findByCode(code);
    }

    @Named("resolveCodeSkill")
    public List<Skill> resolveCodeSkill(final List<String> codeList) {
        final var skillList = new ArrayList<Skill>();
        for (var code : codeList) {
            skillList.add(resolveCodeSkill(code));
        }
        return skillList;
    }

    public Opportunity resolveCodeOpportunity(final String code) { return opportunityService.findByCode(code); }

    @Named("resolveCodeOpportunity")
    public List<Opportunity> resolveCodeOpportunity(final List<String> codeList) {
        final var opportunityList = new ArrayList<Opportunity>();
        for (var code : codeList) {
            opportunityList.add(resolveCodeOpportunity(code));
        }
        return opportunityList;
    }

}
