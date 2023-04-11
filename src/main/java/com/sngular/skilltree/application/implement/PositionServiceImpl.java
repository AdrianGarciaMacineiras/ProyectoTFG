package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.infraestructura.PositionRepository;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

  private final PositionRepository positionRepository;

  private final CandidateService candidateService;

  @Override
  public List<Position> getAll() {
    return positionRepository.findAll();
  }

  @Override
  public Position create(final Position position) {
    validateExist(position.code());
    positionRepository.save(position);
    candidateService.generateCandidates(position.code(), position.skills());
    var newPosition = positionRepository.findByCode(position.code());
    return newPosition;
  }

  @Override
  public Position findByCode(final String positioncode) {
    var position = positionRepository.findByCode(positioncode);
    if (Objects.isNull(position) || position.deleted()) {
      throw new EntityNotFoundException("Position", positioncode);
    }
    return position;
  }

  @Override
  public boolean deleteByCode(final String positioncode) {
    validateDoesNotExist(positioncode);
    return positionRepository.deleteByCode(positioncode);
  }

  @Override
  public Position generateCandidates(String positionCode) {
    var position = positionRepository.findByCode(positionCode);
    if (Objects.isNull(position) || position.deleted()) {
      throw new EntityNotFoundException("Position", positionCode);
    }
    candidateService.generateCandidates(position.code(), position.skills());
    var newPosition = positionRepository.findByCode(positionCode);
    return newPosition;
  }

  @Override
  public Position assignCandidate(String positionCode, Long peopleCode) {
    validateDoesNotExist(positionCode);
    candidateService.assignCandidate(positionCode, peopleCode);
    return positionRepository.findByCode(positionCode);
  }


  private void validateExist(String code) {
    var oldPosition = positionRepository.findByCode(code);
    if (!Objects.isNull(oldPosition) && !oldPosition.deleted()) {
      throw new EntityFoundException("Position", code);
    }
  }

  private void validateDoesNotExist(String code) {
    var oldPosition = positionRepository.findByCode(code);
    if (Objects.isNull(oldPosition) || oldPosition.deleted()) {
      throw new EntityNotFoundException("Position", code);
    }
  }
}
