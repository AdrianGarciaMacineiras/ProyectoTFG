package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OpportunityNodeMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpportunityRepositoryImpl implements OpportunityRepository {

  private final OpportunityCrudRepository crud;

  private final OpportunityNodeMapper mapper;
  @Override
  public List<Opportunity> findAll() {
    return mapper.map(crud.findAll());
  }

  @Override
  public Opportunity save(Opportunity opportunity) {
    return mapper.fromNode(crud.save(mapper.toNode(opportunity)));
  }

  @Override
  public Opportunity findByCode(String opportunitycode) {
    return mapper.fromNode(crud.findByCode(opportunitycode));
  }

  @Override
  public boolean deleteByCode(String opportunitycode) {
    var node = crud.findByCode(opportunitycode);
    crud.delete(node);
    return true;
  }

}
