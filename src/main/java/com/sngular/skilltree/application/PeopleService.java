package com.sngular.skilltree.application;

import java.util.List;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.views.PeopleNamesView;
import com.sngular.skilltree.model.views.PeopleView;

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

  List<People> getPeopleBySkills(final List<String> skills);

    List<People> getOtherPeopleStrategicSkills(final String teamCode);

    List<Position> getPeopleAssignedPositions(final String peopleCode);
}
