package com.sngular.skilltree.infraestructura;

import java.util.List;
import java.util.Optional;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.PositionSkill;

public interface CandidateRepository {

    List<Candidate> findAll();

    Candidate save(Candidate candidate);

    Candidate findByCode(String candidateCode);

    boolean deleteByCode(String candidateCode);

    List<Candidate> findAllCandidates();

    List<Candidate> generateCandidates(String positionCode, List<PositionSkill> positionSkills);

    Optional<Candidate> findByPeopleAndPosition(String positionCode, String peopleCode);

    void assignCandidate(String positionCode, String peopleCandidate, Candidate candidates);

    List<Candidate> getCandidatesByPosition(String positionCode);

    List<Candidate> getCandidatesByPeople(String peopleCode);

}