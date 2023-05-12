package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import com.sngular.skilltree.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = ResolveServiceNode.class)
public interface SkillNodeMapper {

    @Mapping(target = "subSkills", source = "subSkills", qualifiedByName = {"resolveServiceNode", "mapToSubskill"})
    Skill fromNode(SkillNode skillNode);

    @Mapping(target = "subSkills", source = "subSkills", qualifiedByName = {"resolveServiceNode", "mapToSkillRelationship"})
    SkillNode toNode (Skill skill);

    List<Skill> map(List<SkillNode> all);
}
