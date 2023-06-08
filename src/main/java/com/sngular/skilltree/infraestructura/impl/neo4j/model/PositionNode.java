package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

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

    private boolean deleted;

    private boolean open;

    private LocalDate openingDate;

    private LocalDate closingDate;

    private String priority;

    private EnumMode mode;

    private String role;

    @Relationship(type="FOR_PROJECT", direction = Relationship.Direction.OUTGOING)
    private ProjectNode project;

    @Relationship(type="IN_THE_OFFICE", direction = Relationship.Direction.OUTGOING)
    private OfficeNode office;

    @Relationship(type="MANAGED_BY", direction = Relationship.Direction.OUTGOING)
    private PeopleNode managedBy;

    @Relationship(type="NEEDS", direction = Relationship.Direction.OUTGOING)
    private List<PositionSkillsRelationship> skills;

    @Relationship(type = "CANDIDATE", direction = Relationship.Direction.OUTGOING)
    private List<CandidateRelationship> candidates;

}
