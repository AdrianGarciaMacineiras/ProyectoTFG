package com.sngular.skilltree.skill.service;

import com.sngular.skilltree.skill.model.Skill;
import com.sngular.skilltree.skill.repository.SkillRepository;
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
