package com.sngular.skilltree.application;

import com.sngular.skilltree.application.updater.CandidateUpdater;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        validate(candidate);
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate findByCode(String candidatecode) {
        return candidateRepository.findByCode(candidatecode);
    }

    @Override
    public boolean deleteByCode(String candidatecode) {
        return candidateRepository.deleteByCode(candidatecode);
    }

    private void validate(Candidate candidate) {
    }
}
