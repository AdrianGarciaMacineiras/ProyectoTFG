package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import com.sngular.skilltree.model.Opportunity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = SkillNodeMapper.class)
public interface OpportunityNodeMapper {


  OpportunityNode toNode(Opportunity opportunity);

  Opportunity fromNode(OpportunityNode opportunityNode);

  List<Opportunity> map(List<OpportunityNode> all);
}
