package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionSkill;

import java.util.List;

public interface CandidateRepository {

    List<Candidate> findAll();

    Candidate save(Candidate candidate);

    Candidate findByCode(String candidatecode);

    boolean deleteByCode(String candidatecode);

    List<Candidate> findAllCandidates();

    List<Candidate> generateCandidates(String positionCode, List<PositionSkill> positionSkills);

}