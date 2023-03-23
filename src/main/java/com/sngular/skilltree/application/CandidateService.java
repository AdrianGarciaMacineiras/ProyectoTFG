package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate findByCode(final String candidatecode);

    boolean deleteByCode(final String candidatecode);
}
