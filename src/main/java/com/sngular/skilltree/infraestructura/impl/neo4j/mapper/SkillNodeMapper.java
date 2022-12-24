package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillNodeMapper {

    Skill fromNode(SkillNode skillNode);

    List<Skill> map(List<SkillNode> all);
}
