package com.sngular.skilltree.application;

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
}
