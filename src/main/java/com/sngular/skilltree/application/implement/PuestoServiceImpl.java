package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.PuestoService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.Puesto;
import com.sngular.skilltree.infraestructura.PuestoRepository;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PuestoServiceImpl implements PuestoService {

  private final PuestoRepository puestoRepository;

  @Override
  public List<Puesto> getAll() {
    return puestoRepository.findAll();
  }

  @Override
  public Puesto create(final Puesto puesto) {
    validateExist(puesto.code());
    return puestoRepository.save(puesto);
  }

  @Override
  public Puesto findByCode(final String opportunitycode) {
    var opportunity = puestoRepository.findByCode(opportunitycode);
    if (Objects.isNull(opportunity) || opportunity.deleted()) {
      throw new EntityNotFoundException("Opportunity", opportunitycode);
    }
    return opportunity;
  }

  @Override
  public boolean deleteByCode(final String opportunitycode) {
    validateDoesNotExist(opportunitycode);
    return puestoRepository.deleteByCode(opportunitycode);
  }


  private void validateExist(String code) {
    var oldOpportunity = puestoRepository.findByCode(code);
    if (!Objects.isNull(oldOpportunity) && !oldOpportunity.deleted()) {
      throw new EntityFoundException("Opportunity", code);
    }
  }

  private void validateDoesNotExist(String code) {
    var oldOpportunity = puestoRepository.findByCode(code);
    if (Objects.isNull(oldOpportunity) || oldOpportunity.deleted()) {
      throw new EntityNotFoundException("Opportunity", code);
    }
  }
}
