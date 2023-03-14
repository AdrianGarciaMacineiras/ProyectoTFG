package com.sngular.skilltree.application;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.*;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService{

    private final CandidateRepository candidateRepository;

    @Override
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate create(Candidate candidate) {
        validateExist(candidate.code());
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate findByCode(String candidatecode) {
        var candidate = candidateRepository.findByCode(candidatecode);
        if(Objects.isNull(candidate) || candidate.deleted()){
            throw new EntityNotFoundException("Candidate", candidatecode);
        }
        return candidate;
    }

    @Override
    public boolean deleteByCode(String candidatecode) {
        validateDoesNotExist(candidatecode);
        return candidateRepository.deleteByCode(candidatecode);
    }


    private void validateExist(String code) {
        var oldCandidate = candidateRepository.findByCode(code);
        if (!Objects.isNull(oldCandidate) && !oldCandidate.deleted()) {
            throw new EntityFoundException("Candidate", code);
        }
    }

    private void validateDoesNotExist(String code) {
        var oldCandidate = candidateRepository.findByCode(code);
        if (Objects.isNull(oldCandidate) && oldCandidate.deleted()) {
            throw new EntityNotFoundException("Candidate", code);
        }
    }
}
