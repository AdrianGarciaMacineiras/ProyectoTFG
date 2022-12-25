package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.infraestructura.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillRepository {

    private final SkillRepository skillRepository;

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findByCode(String skillcode) {
        return skillRepository.findByCode(skillcode);
    }
}
