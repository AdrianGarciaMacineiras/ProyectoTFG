package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.PositionSkill;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate findByCode(final String candidateCode);

    boolean deleteByCode(final String candidateCode);

    List<Candidate> generateCandidates(String positionCode, List<PositionSkill> positionSkills);

    void assignCandidate(final String positionCode, final String peopleCode);

    List<Candidate> getCandidatesByPosition(final String positionCode);

    List<Candidate> getCandidatesByPeople(final String peopleCode);

}
