package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Candidate;
import java.util.List;

public interface CandidateRepository {

    List<Candidate> findAll();

    Candidate save(Candidate candidate);

    Candidate findByCode(String candidatecode);

    boolean deleteByCode(String candidatecode);
}