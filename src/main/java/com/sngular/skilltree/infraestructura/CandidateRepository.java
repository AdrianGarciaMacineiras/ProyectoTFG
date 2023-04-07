package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Position;

import java.util.List;

public interface CandidateRepository {

    List<Candidate> findAll();

    Candidate save(Candidate candidate);

    Candidate findByCode(String candidatecode);

    boolean deleteByCode(String candidatecode);

    List<Candidate> findAllCandidates();

    List<Candidate> generateCandidates(Position position);

}