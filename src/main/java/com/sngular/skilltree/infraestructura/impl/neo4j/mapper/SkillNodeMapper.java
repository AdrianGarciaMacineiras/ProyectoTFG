package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import com.sngular.skilltree.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CommonMapperConfiguration.class, uses = ResolveServiceNode.class)
public interface SkillNodeMapper {

    @Mapping(target = "subSkillList", source = "subSkills", qualifiedByName = {"resolveServiceNode", "mapToSubskill"})
    Skill fromNode(SkillNode skillNode);

    @Mapping(target = "subSkills", source = "subSkillList", qualifiedByName = {"resolveServiceNode", "mapToSkillRelationship"})
    SkillNode toNode (Skill skill);

    List<Skill> map(List<SkillNode> all);
}
