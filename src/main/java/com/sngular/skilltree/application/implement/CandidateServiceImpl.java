package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Position;
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
    public Candidate findByCode(String candidatecode) {
        var candidate = candidateRepository.findByCode(candidatecode);
        if(Objects.isNull(candidate)){
            throw new EntityNotFoundException("Candidate", candidatecode);
        }
        return candidate;
    }

    @Override
    public boolean deleteByCode(String candidatecode) {
        validateDoesNotExist(candidatecode);
        return candidateRepository.deleteByCode(candidatecode);
    }

    @Override
    public List<Candidate> generateCandidates(String positioncode, List<PositionSkill> positionSkills) {
        return candidateRepository.generateCandidates(positioncode, positionSkills);
    }

    @Override
    public void assignCandidate(String positionCode, Long peopleCode) {
        var candidates = candidateRepository.findByPeopleandPosition(positionCode, peopleCode);
        if (Objects.isNull(candidates) || candidates.isEmpty()){
            throw new EntityNotFoundException("Candidate", positionCode, peopleCode);
        }
        candidateRepository.assignCandidate(positionCode, peopleCode, candidates);
    }

    @Override
    public List<Candidate> getCandidates(String positionCode) {
        return candidateRepository.getCandidates(positionCode);
    }

    @Override
    public List<Candidate> getCandidates(Long peopleCode) {
        return candidateRepository.getCandidates(peopleCode);
    }

    private void validateDoesNotExist(String code) {
        var oldCandidate = candidateRepository.findByCode(code);
        if (Objects.isNull(oldCandidate)) {
            throw new EntityNotFoundException("Candidate", code);
        }
    }

}
