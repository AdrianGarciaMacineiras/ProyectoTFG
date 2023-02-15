package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import com.sngular.skilltree.model.Opportunity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillNodeMapper.class, PeopleNodeMapper.class, ResolveRepository.class})
public interface OpportunityNodeMapper {

  @Mapping(target = "office", source = "office", qualifiedByName = {"resolveRepository", "resolveCodeToOfficeNode"})
  @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveRepository", "resolveCodeToPeopleNode"})
  OpportunityNode toNode(Opportunity opportunity);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "office", source = "office.name")
  @Mapping(target = "managedBy", source = "managedBy.name")
  Opportunity fromNode(OpportunityNode opportunityNode);

  List<Opportunity> map(List<OpportunityNode> all);
}
