package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ResolveServiceNode.class)
public interface SkillNodeMapper {

    @Mapping(target = "subSkills", source = "subSkills", qualifiedByName = {"resolveServiceNode", "mapToSubskill"})
    Skill fromNode(SkillNode skillNode);

    @Mapping(target = "subSkills", source = "subSkills", qualifiedByName = {"resolveServiceNode", "mapToSkillRelationship"})
    SkillNode toNode (Skill skill);

    List<Skill> map(List<SkillNode> all);
}
