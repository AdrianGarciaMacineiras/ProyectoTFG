package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Client;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate create(final Candidate candidate);

    Candidate findByCode(final String candidatenode);

    boolean deleteBeCode(final String candidatenode);

    Candidate update(final String candidatenode, final Candidate newCandidate);

    Candidate patch(final String candidatecode, final Candidate patchedCandidate);
}
