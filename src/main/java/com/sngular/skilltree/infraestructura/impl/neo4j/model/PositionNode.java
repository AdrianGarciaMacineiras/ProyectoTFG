package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Node("Position")
public class PositionNode {

    @Id
    private String code;

    private String name;

    @Relationship(type="FOR_PROJECT", direction = Relationship.Direction.OUTGOING)
    private ProjectNode project;

    @Relationship(type="FOR_CLIENT", direction = Relationship.Direction.OUTGOING)
    private ClientNode client;

    private LocalDate openingDate;

    private LocalDate closingDate;

    private String priority;

    private EnumMode mode;

    @Relationship(type="IN_THE_OFFICE", direction = Relationship.Direction.OUTGOING)
    private OfficeNode office;

    @Relationship(type="MANAGED_BY", direction = Relationship.Direction.OUTGOING)
    private PeopleNode managedBy;

    private String role;

    @Relationship(type="NEEDS", direction = Relationship.Direction.OUTGOING)
    private List<PositionSkillsRelationship> skills;

    @Relationship(type = "CANDIDATE", direction = Relationship.Direction.OUTGOING)
    private List<CandidateRelationship> candidates;

    private boolean deleted;

    private boolean open;

}