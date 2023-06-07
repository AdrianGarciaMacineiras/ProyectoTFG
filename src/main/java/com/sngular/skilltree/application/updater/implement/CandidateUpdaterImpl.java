package com.sngular.skilltree.application.updater.implement;

import com.sngular.skilltree.application.updater.CandidateUpdater;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateUpdaterImpl implements CandidateUpdater {

    private final CandidateRepository candidateRepository;

    private final CandidateMapper mapper;

    @Override
    public Candidate update(String candidateCode, Candidate newCandidate) {
        var oldCandidate = candidateRepository.findByCode(candidateCode);
        return candidateRepository.save(newCandidate);
    }

    @Override
    public Candidate patch(String candidateCode, Candidate patchedCandidate) {
        var oldCandidate = candidateRepository.findByCode(candidateCode);
        var client = mapper.update(oldCandidate, patchedCandidate);
        return candidateRepository.save(client);
    }

}
