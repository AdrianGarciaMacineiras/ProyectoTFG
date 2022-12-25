package com.sngular.skilltree.application;

import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

  private final OpportunityRepository opportunityRepository;

  private final OpportunityMapper mapper;

  @Override
  public List<Opportunity> getAll() {
    return opportunityRepository.findAll();
  }

  @Override
  public Opportunity create(final Opportunity opportunity) {
    validate(opportunity);
    return opportunityRepository.save(opportunity);
  }

  @Override
  public Opportunity findByCode(final String opportunitycode) {
    return opportunityRepository.findByCode(opportunitycode);
  }

  @Override
  public boolean deleteBeCode(final String opportunitycode) {
    return opportunityRepository.deleteByCode(opportunitycode);
  }

  @Override
  public Opportunity update(final String opportunitycode, final Opportunity newOpportunity) {
    var oldOpportunity = opportunityRepository.findByCode(opportunitycode);
    mapper.update(oldOpportunity, newOpportunity);
    return opportunityRepository.save(oldOpportunity);
  }

  @Override
  public Opportunity patch(final String opportunitycode, final Opportunity patchedOpportunity) {
    var oldOpportunity = opportunityRepository.findByCode(opportunitycode);
    mapper.update(oldOpportunity, patchedOpportunity);
    opportunityRepository.save(oldOpportunity);
    return opportunityRepository.save(oldOpportunity);
  }

  private void validate(Opportunity opportunity) {
  }
}
