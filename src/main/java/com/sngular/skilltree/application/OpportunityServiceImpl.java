package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

  private final OpportunityRepository opportunityRepository;

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
  public boolean deleteByCode(final String opportunitycode) {
    return opportunityRepository.deleteByCode(opportunitycode);
  }

  private void validate(Opportunity opportunity) {
  }
}
