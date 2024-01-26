package com.tfg.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;
import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.tfg.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import com.tfg.skilltree.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = ResolveServiceNode.class)
public interface SkillNodeMapper {

    @Mapping(target = "subSkillList", source = "subSkills", qualifiedByName = {"resolveServiceNode", "mapToSubskill"})
    Skill fromNode(SkillNode skillNode);

    @Mapping(target = "subSkills", source = "subSkillList", qualifiedByName = {"resolveServiceNode", "mapToSkillRelationship"})
    SkillNode toNode (Skill skill);

    List<Skill> map(List<SkillNode> all);
}
