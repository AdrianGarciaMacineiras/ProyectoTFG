package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.infraestructura.SkillRepository;
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
}
