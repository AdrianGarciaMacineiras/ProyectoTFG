package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;

import java.util.List;

public interface PeopleService {

    List<People> getAll();

    People create (final People toPeople);

    People findByCode(final Long personcode);

    People findPeopleByCode(final Long personcode);

    boolean deleteByCode(final Long personcode);

    People assignCandidate(final Long peopleCode, final String positionCode);

    List<Candidate> getCandidates(final Long peopleCode);

    List<People> getPeopleSkills(final List<String> skills);

    List<People> getOtherPeopleStrategicSkills(final String teamcode);

    List<Position> getPeopleAssignedPositions(final Long peoplecode);
}
