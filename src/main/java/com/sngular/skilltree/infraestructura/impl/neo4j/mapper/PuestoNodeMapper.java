package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PuestoNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PuestoSkillsRelationship;
import com.sngular.skilltree.model.Puesto;
import com.sngular.skilltree.model.PuestoSkill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillNodeMapper.class, PeopleNodeMapper.class, OfficeNodeMapper.class,
        ProjectNodeMapper.class, CandidateNodeMapper.class})
public interface PuestoNodeMapper {

  @InheritInverseConfiguration
  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  PuestoNode toNode(Puesto puesto);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  Puesto fromNode(PuestoNode puestoNode);

  @Mapping(target = "min_exp", source = "minExp")
  @Mapping(target = "min_level", source = "minLevel")
  @Mapping(target = "req_level", source = "levelReq")
  PuestoSkillsRelationship toOpportunitySkillsRelationship(PuestoSkill puestoSkill);

  @InheritInverseConfiguration
  PuestoSkill toOpportunitySkill(PuestoSkillsRelationship puestoSkillsRelationship);

  List<Puesto> map(List<PuestoNode> all);
}
