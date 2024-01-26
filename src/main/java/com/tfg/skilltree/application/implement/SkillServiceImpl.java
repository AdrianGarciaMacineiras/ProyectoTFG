package com.tfg.skilltree.application.implement;

import java.util.List;
import com.tfg.skilltree.application.SkillService;
import com.tfg.skilltree.infraestructura.SkillRepository;
import com.tfg.skilltree.model.Skill;
import com.tfg.skilltree.model.StrategicTeamSkill;
import com.tfg.skilltree.model.StrategicTeamSkillNotUsed;
import com.tfg.skilltree.model.views.SkillStatsTittle;
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
