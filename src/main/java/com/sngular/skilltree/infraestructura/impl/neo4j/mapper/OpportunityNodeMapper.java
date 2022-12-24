package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import java.util.List;

import org.mapstruct.Mapper;

@Mapper(uses = SkillMapper.class)
public interface OpportunityNodeMapper {


  OpportunityNode toNode(Opportunity opportunity);

  Opportunity fromNode(OpportunityNode opportunityNode);

  List<Opportunity> map(List<OpportunityNode> all);
}
