package com.sngular.skilltree.application.implement;

import java.util.List;
import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.infraestructura.SkillRepository;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;
import com.sngular.skilltree.model.views.SkillStatsTittle;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    @Cacheable("skills")
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    @Override
    //@Cacheable(cacheNames = "skills")
    public Skill findByCode(final String skillCode) {
        return skillRepository.findByCode(skillCode);
    }

    @Override
    //@Cacheable(cacheNames = "skills")
    public Skill findSkill(final String skillCode) {
        return skillRepository.findSkill(skillCode);
    }

    @Override
    //@Cacheable(cacheNames = "skills")
    public Skill findByName(final String skillName) {
        return skillRepository.findByName(skillName);
    }

    @Override
    public List<StrategicTeamSkill> getStrategicSkillsUse() {
        return skillRepository.getStrategicSkillsUse();
    }

    @Override
    public List<StrategicTeamSkillNotUsed> getNoStrategicSkillsUse() {
        return skillRepository.getNoStrategicSkillsUse();
    }

    @Override
    public SkillStatsTittle getSkillStatsByTittle(final String tittle) {
        return skillRepository.getSkillStatsByTittle(tittle);
    }
}
