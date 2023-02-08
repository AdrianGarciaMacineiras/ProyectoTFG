package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateUpdaterImpl implements CandidateUpdater{
    private final CandidateRepository candidateRepository;

    private final CandidateMapper mapper;

    @Override
    public Candidate update(String candidatecode, Candidate newCandidate) {
        var oldCandidate = candidateRepository.findByCode(candidatecode);
        mapper.update(oldCandidate, newCandidate);
        return candidateRepository.save(oldCandidate);
    }

    @Override
    public Candidate patch(String candidatecode, Candidate patchedCandidate) {
        var oldCandidate = candidateRepository.findByCode(candidatecode);
        mapper.update(oldCandidate, patchedCandidate);
        return candidateRepository.save(oldCandidate);
    }
}
