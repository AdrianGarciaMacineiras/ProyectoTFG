package com.sngular.skilltree.opportunity.service;

import com.sngular.skilltree.opportunity.mapper.OpportunityMapper;
import com.sngular.skilltree.opportunity.model.Opportunity;
import com.sngular.skilltree.opportunity.repository.OpportunityRepository;
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
    opportunityRepository.save(oldOpportunity);
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
