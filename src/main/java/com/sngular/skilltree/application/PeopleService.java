package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;

import java.util.List;

public interface PeopleService {

    List<People> getAll();

    People create (final People toPeople);

    People findByCode(final String personCode);

    People findPeopleByCode(final String personCode);

    boolean deleteByCode(final String personCode);

    People assignCandidate(final String peopleCode, final String positionCode);

    List<Candidate> getCandidates(final String peopleCode);

    List<People> getPeopleSkills(final List<String> skills);

    List<People> getOtherPeopleStrategicSkills(final String teamCode);

    List<Position> getPeopleAssignedPositions(final String peopleCode);
}
