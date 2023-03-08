package com.sngular.skilltree.application;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import java.util.List;
import java.util.Objects;

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
    validateExist(opportunity.code());
    return opportunityRepository.save(opportunity);
  }

  @Override
  public Opportunity findByCode(final String opportunitycode) {
    return opportunityRepository.findByCode(opportunitycode);
  }

  @Override
  public boolean deleteByCode(final String opportunitycode) {
    validateDoesntExist(opportunitycode);
    return opportunityRepository.deleteByCode(opportunitycode);
  }


  private void validateExist(String code) {
    var oldOpportunity = opportunityRepository.findByCode(code);
    if (!Objects.isNull(oldOpportunity)) {
      throw new EntityFoundException("Opportunity", code);
    }
  }

  private void validateDoesntExist(String code) {
    var oldOpportunity = opportunityRepository.findByCode(code);
    if (Objects.isNull(oldOpportunity)) {
      throw new EntityNotFoundException("Opportunity", code);
    }
  }
}
