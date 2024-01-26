package com.tfg.skilltree.application;

import java.util.List;

import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.model.People;
import com.tfg.skilltree.model.PeopleSkill;
import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.views.PeopleNamesView;
import com.tfg.skilltree.model.views.PeopleView;

public interface PeopleService {

    List<People> getAll();

    List<PeopleView> getAllResumed();

    List<PeopleNamesView> getAllNames();

    People create (final People toPeople);

    People findByCode(final String personCode);

    People findPeopleByCode(final String personCode);

    boolean deleteByCode(final String personCode);

    People assignCandidate(final String peopleCode, final String positionCode);

    List<Candidate> getCandidates(final String peopleCode);

  List<People> getPeopleBySkills(final List<PeopleSkill> skills);

    List<People> getOtherPeopleStrategicSkills(final String teamCode);

    List<Position> getPeopleAssignedPositions(final String peopleCode);
}
