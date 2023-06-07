package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.PositionSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public Candidate findByCode(String candidateCode) {
        var candidate = candidateRepository.findByCode(candidateCode);
        if (Objects.isNull(candidate)) {
            throw new EntityNotFoundException("Candidate", candidateCode);
        }
        return candidate;
    }

    @Override
    public boolean deleteByCode(String candidateCode) {
        validateDoesNotExist(candidateCode);
        return candidateRepository.deleteByCode(candidateCode);
    }

    @Override
    public List<Candidate> generateCandidates(String positionCode, List<PositionSkill> positionSkills) {
        return candidateRepository.generateCandidates(positionCode, positionSkills);
    }

    @Override
    public void assignCandidate(String positionCode, String peopleCode) {
        var candidates = candidateRepository.findByPeopleAndPosition(positionCode, peopleCode);
        if (Objects.isNull(candidates) || candidates.isEmpty()) {
            throw new EntityNotFoundException("Candidate", positionCode, peopleCode);
        }
        candidateRepository.assignCandidate(positionCode, peopleCode, candidates);
    }

    @Override
    public List<Candidate> getCandidatesByPosition(String positionCode) {
        return candidateRepository.getCandidatesByPosition(positionCode);
    }

    @Override
    public List<Candidate> getCandidatesByPeople(String peopleCode) {
        return candidateRepository.getCandidatesByPosition(peopleCode);
    }

    private void validateDoesNotExist(String code) {
        var oldCandidate = candidateRepository.findByCode(code);
        if (Objects.isNull(oldCandidate)) {
            throw new EntityNotFoundException("Candidate", code);
        }
    }

}
