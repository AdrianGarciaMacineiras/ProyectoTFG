package com.tfg.skilltree.application;

import java.util.List;
import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.views.PositionView;

public interface PositionService {

  List<Position> getAll();

  List<PositionView> getAllResumed();

  Position create(final Position toPosition);

  Position findByCode(final String positionCode);

  boolean deleteByCode(final String positionCode);

  Position generateCandidates(final String positionCode);

  Position assignCandidate(final String positionCode, final String peopleCode);

  List<Candidate> getCandidates(final String positionCode);

    List<Position> getPeopleAssignedPositions(final String peopleCode);

}