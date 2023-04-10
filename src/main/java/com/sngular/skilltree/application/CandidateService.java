package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.PositionSkill;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate findByCode(final String candidatecode);

    boolean deleteByCode(final String candidatecode);

    List<Candidate> generateCandidates(String positioncode, List<PositionSkill> positionSkills);
}
