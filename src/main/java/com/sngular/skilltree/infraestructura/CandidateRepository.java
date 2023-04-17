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

    List<Candidate> findByPeopleandPosition(String positionCode, Long peopleCode);

    void assignCandidate(String positionCode, Long peopleCandidate, List<Candidate> candidates);

    List<Candidate> getCandidates(String positionCode);

    List<Candidate> getCandidates(Long peopleCode);

}