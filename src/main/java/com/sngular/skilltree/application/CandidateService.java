package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate create(final Candidate candidate);

    Candidate findByCode(final String candidatenode);

    boolean deleteByCode(final String candidatenode);

    Candidate update(final String candidatecode, final Candidate newCandidate);

    Candidate patch(final String candidatecode, final Candidate patchedCandidate);
}
