package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionSkillsRelationship;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionSkill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {SkillNodeMapper.class, PeopleNodeMapper.class, OfficeNodeMapper.class,
  ProjectNodeMapper.class, CandidateNodeMapper.class, ResolveServiceNode.class})
public interface PositionNodeMapper {

  @InheritInverseConfiguration
  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  PositionNode toNode(Position position);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  Position fromNode(PositionNode positionNode);

  @Mapping(target = "min_exp", source = "minExp")
  @Mapping(target = "min_level", source = "minLevel")
  @Mapping(target = "req_level", source = "levelReq")
  @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
  PositionSkillsRelationship toPositionSkillsRelationship(PositionSkill positionSkill);

  @InheritInverseConfiguration
  PositionSkill toPositionSkill(PositionSkillsRelationship positionSkillsRelationship);

  List<Position> map(List<PositionNode> all);
}
