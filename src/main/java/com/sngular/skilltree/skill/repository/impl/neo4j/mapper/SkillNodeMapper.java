package com.sngular.skilltree.skill.repository.impl.neo4j.mapper;

import com.sngular.skilltree.skill.model.Skill;
import com.sngular.skilltree.skill.repository.impl.neo4j.model.SkillNode;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface SkillNodeMapper {

    Skill fromNode(SkillNode skillNode);

    List<Skill> map(List<SkillNode> all);
}
