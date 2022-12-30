package com.sngular.skilltree.application;

import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService{

    private final CandidateRepository candidateRepository;

    private final CandidateMapper mapper;

    @Override
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate create(Candidate candidate) {
        validate(candidate);
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate findByCode(String candidatenode) {
        return candidateRepository.findByCode(candidatenode);
    }

    @Override
    public boolean deleteBeCode(String candidatenode) {
        return candidateRepository.deleteByCode(candidatenode);
    }

    @Override
    public Candidate update(String candidatenode, Candidate newCandidate) {
        var oldCandidate = candidateRepository.findByCode(candidatenode);
        mapper.update(oldCandidate, newCandidate);
        return candidateRepository.save(oldCandidate);
    }

    @Override
    public Candidate patch(String candidatecode, Candidate patchedCandidate) {
        var oldCandidate = candidateRepository.findByCode(candidatecode);
        mapper.update(oldCandidate, patchedCandidate);
        return candidateRepository.save(oldCandidate);
    }

    private void validate(Candidate candidate) {
    }
}
