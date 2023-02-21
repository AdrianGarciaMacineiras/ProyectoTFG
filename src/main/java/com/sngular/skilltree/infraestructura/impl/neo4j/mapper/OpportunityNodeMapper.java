package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunitySkillsRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.projections.OpportunityProjection;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.OpportunitySkill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillNodeMapper.class, PeopleNodeMapper.class, OfficeNodeMapper.class, ProjectNodeMapper.class})
public interface OpportunityNodeMapper {

  @InheritInverseConfiguration
  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  OpportunityNode toNode(Opportunity opportunity);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  Opportunity fromNode(OpportunityProjection opportunityNode);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  Opportunity fromNode(OpportunityNode opportunityNode);

  @Mapping(target = "min_exp", source = "minExp")
  @Mapping(target = "min_level", source = "minLevel")
  @Mapping(target = "req_level", source = "levelReq")
  OpportunitySkillsRelationship toOpportunitySkillsRelationship(OpportunitySkill opportunitySkill);

  @InheritInverseConfiguration
  OpportunitySkill toOpportunitySkill(OpportunitySkillsRelationship opportunitySkillsRelationship);

  List<Opportunity> map(List<OpportunityNode> all);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  OpportunityNode fromOpportunityProjection(OpportunityProjection opportunityProjection);
}
