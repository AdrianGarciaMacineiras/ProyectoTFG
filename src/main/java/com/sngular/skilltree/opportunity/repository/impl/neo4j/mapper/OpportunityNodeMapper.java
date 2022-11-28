package com.sngular.skilltree.opportunity.repository.impl.neo4j.mapper;

import com.sngular.skilltree.opportunity.model.Opportunity;
import com.sngular.skilltree.opportunity.repository.impl.neo4j.model.OpportunityNode;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface OpportunityNodeMapper {


  @InheritInverseConfiguration
  OpportunityNode toNode(Opportunity opportunity);

  Opportunity fromNode(OpportunityNode opportunityNode);
  List<Opportunity> map(List<OpportunityNode> all);
}
