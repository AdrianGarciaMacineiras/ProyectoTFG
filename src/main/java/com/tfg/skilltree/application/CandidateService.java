package com.tfg.skilltree.application;

import java.util.List;

import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.model.PositionSkill;

public interface CandidateService {

    List<Candidate> getAll();

    Candidate findByCode(final String candidateCode);

    boolean deleteByCode(final String candidateCode);

    List<Candidate> generateCandidates(String positionCode, List<PositionSkill> positionSkills);

    void assignCandidate(final String positionCode, final String peopleCode);

    List<Candidate> getCandidatesByPosition(final String positionCode);

    List<Candidate> getCandidatesByPeople(final String peopleCode);

}