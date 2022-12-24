package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.infraestructura.SkillRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.SkillNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepository {

    private final SkillCrudRepository crud;

    private final SkillNodeMapper mapper;

    @Override
    public List<Skill> findAll() {
        var skillNodeList = crud.findAll();
        List<Skill> subSkills = null;
        List<Skill> skills = null;
        for(SkillNode skillNode: skillNodeList){
            for (SkillNode subSkillNode: skillNode.getSubSkills()){
                var toSkill = mapper.fromNode(subSkillNode);
                subSkills.add(toSkill);
            }
            Skill skill = new Skill(skillNode.getCode(), skillNode.getName(), subSkills);
            skills.add(skill);
        }
        return skills;
    }

    @Override
    public Skill findByCode(String skillcode) {
        var skillNode = crud.findByCode(skillcode);
        List<Skill> subSkills = null;
        for(SkillNode subSkillNode: skillNode.getSubSkills()){
            var toSkill = mapper.fromNode(subSkillNode);
            subSkills.add(toSkill);
        }
        Skill skill = new Skill(skillcode, skillNode.getName(), subSkills);
        return skill;
    }

}
