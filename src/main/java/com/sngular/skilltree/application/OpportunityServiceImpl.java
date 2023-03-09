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
    return opportunityRepository.findByDeletedIsFalse();
  }

  @Override
  public Opportunity create(final Opportunity opportunity) {
    validateExist(opportunity.code());
    return opportunityRepository.save(opportunity);
  }

  @Override
  public Opportunity findByCode(final String opportunitycode) {
    var opportunity = opportunityRepository.findByCode(opportunitycode);
    if (Objects.isNull(opportunity) || opportunity.deleted()) {
      throw new EntityNotFoundException("Opportunity", opportunitycode);
    }
    return opportunity;
  }

  @Override
  public boolean deleteByCode(final String opportunitycode) {
    validateDoesNotExist(opportunitycode);
    return opportunityRepository.deleteByCode(opportunitycode);
  }


  private void validateExist(String code) {
    var oldOpportunity = opportunityRepository.findByCode(code);
    if (!Objects.isNull(oldOpportunity) && !oldOpportunity.deleted()) {
      throw new EntityFoundException("Opportunity", code);
    }
  }

  private void validateDoesNotExist(String code) {
    var oldOpportunity = opportunityRepository.findByCode(code);
    if (Objects.isNull(oldOpportunity) || oldOpportunity.deleted()) {
      throw new EntityNotFoundException("Opportunity", code);
    }
  }
}
