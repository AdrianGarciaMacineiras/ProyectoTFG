package com.sngular.skilltree.application.implement;

import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.PositionSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    private final PositionRepository positionRepository;

    @Override
    public List<Candidate> getAll() {
        return candidateRepository.findAllCandidates();
    }

    @Override
    public Candidate findByCode(final String candidateCode) {
        var candidate = candidateRepository.findByCode(candidateCode);
        if (Objects.isNull(candidate)) {
            throw new EntityNotFoundException("Candidate", candidateCode);
        }
        return candidate;
    }

    @Override
    public boolean deleteByCode(final String candidateCode) {
        validateDoesNotExist(candidateCode);
        return candidateRepository.deleteByCode(candidateCode);
    }

    @Override
    @Transactional
    public List<Candidate> generateCandidates(final String positionCode, final List<PositionSkill> positionSkills) {
        return candidateRepository.generateCandidates(positionCode, positionSkills);
    }

    @Override
    public void assignCandidate(final String positionCode, final String peopleCode) {
        var candidate = candidateRepository.findByPeopleAndPosition(positionCode, peopleCode);
        if (candidate.isEmpty()) {
            throw new EntityNotFoundException("Candidate", positionCode, peopleCode);
        }
        candidate.ifPresent(candidate1 -> candidateRepository.assignCandidate(positionCode, peopleCode, candidate1));
    }

    @Override
    public List<Candidate> getCandidatesByPosition(final String positionCode) {
        return candidateRepository.getCandidatesByPosition(positionCode);
    }

    @Override
    public List<Candidate> getCandidatesByPeople(final String peopleCode) {
        return candidateRepository.getCandidatesByPeople(peopleCode);
    }

    private void validateDoesNotExist(final String code) {
        var oldCandidate = candidateRepository.findByCode(code);
        if (Objects.isNull(oldCandidate)) {
            throw new EntityNotFoundException("Candidate", code);
        }
    }

}
