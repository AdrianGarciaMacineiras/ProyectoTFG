package com.tfg.skilltree.application.implement;

import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.application.CandidateService;
import com.tfg.skilltree.application.PositionService;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.PositionRepository;
import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.views.PositionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  //@Cacheable(cacheNames = "positionView")
  public List<PositionView> getAllResumed() {
    return positionRepository.findAllResumed();
  }

  @Override
  //@CacheEvict(cacheNames = "positionView")
  @Transactional
  public Position create(final Position position) {
    validateExist(position.code());
    positionRepository.save(position);
    candidateService.generateCandidates(position.code(), position.skills());
    return positionRepository.findByCode(position.code());
  }

  @Override
  public Position findByCode(final String positionCode) {
    var position = positionRepository.findByCode(positionCode);
    if (Objects.isNull(position) || position.deleted()) {
      throw new EntityNotFoundException("Position", positionCode);
    }
    var assigned = positionRepository.getPeopleAssignedToPosition(positionCode);
    if (!Objects.isNull(position.assignedPeople()))
      position.assignedPeople().clear();
    return position.toBuilder().assignedPeople(assigned).build();
  }

    @Override
    public boolean deleteByCode(final String positionCode) {
        validateDoesNotExist(positionCode);
        return positionRepository.deleteByCode(positionCode);
    }

  @Override
  public Position generateCandidates(final String positionCode) {
    var position = positionRepository.findByCode(positionCode);
    if (Objects.isNull(position) || position.deleted()) {
      throw new EntityNotFoundException("Position", positionCode);
    }
    candidateService.generateCandidates(position.code(), position.skills());
    return positionRepository.findByCode(positionCode);
  }

    @Override
    public Position assignCandidate(final String positionCode, final String peopleCode) {
        validateDoesNotExist(positionCode);
        candidateService.assignCandidate(positionCode, peopleCode);
        return positionRepository.findByCode(positionCode);
    }

  @Override
  public List<Candidate> getCandidates(final String positionCode) {
    validateDoesNotExist(positionCode);
      return candidateService.getCandidatesByPosition(positionCode);
  }

    @Override
    public List<Position> getPeopleAssignedPositions(final String peopleCode) {
        return positionRepository.getPeopleAssignedPositions(peopleCode);
    }

  private void validateExist(final String code) {
    var oldPosition = positionRepository.existByCode(code);
    if (oldPosition) {
      throw new EntityFoundException("Position", code);
    }
  }

  private void validateDoesNotExist(final String code) {
    var oldPosition = positionRepository.findByCode(code);
    if (Objects.isNull(oldPosition) || oldPosition.deleted()) {
      throw new EntityNotFoundException("Position", code);
    }
  }
}
