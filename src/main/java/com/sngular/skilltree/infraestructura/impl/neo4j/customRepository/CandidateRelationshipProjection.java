package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.model.EnumStatus;

import java.time.LocalDate;

public interface CandidateRelationshipProjection {

    String getOpportunityCode();
    String getCode();
    EnumStatus getStatus();
    LocalDate getIntroductionDate();
    LocalDate getResolutionDate();
    PeopleNode getCandidate();
}
