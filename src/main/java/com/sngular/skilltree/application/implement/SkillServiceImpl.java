package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.infraestructura.SkillRepository;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;
import com.sngular.skilltree.model.views.SkillStatsTittle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findByCode(String skillCode) {
        return skillRepository.findByCode(skillCode);
    }

    @Override
    public Skill findSkill(String skillcode) {
        return skillRepository.findSkill(skillcode);
    }

    @Override
    public Skill findByName(String skillName){
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
    public List<SkillStatsTittle> getSkillStatsByTittle(String tittle) {
        return skillRepository.getSkillStatsByTittle(tittle);
    }
}
