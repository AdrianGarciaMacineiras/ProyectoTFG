package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Node("Opportunity")
public class OpportunityNode {

    @Id
    private Long id;

    private String code;

    private String name;

    @Relationship(type="FOR_PROJECT", direction = Relationship.Direction.OUTGOING)
    private ProjectNode project;

    @Relationship(type="FOR_CLIENT", direction = Relationship.Direction.OUTGOING)
    private ClientNode client;

    private String openingDate;

    private String closingDate;

    private String priority;

    private EnumMode enumMode;

    @Relationship(type="IN_THE_OFFICE", direction = Relationship.Direction.OUTGOING)
    private OfficeNode office;

    @Relationship(type="MANAGED_BY", direction = Relationship.Direction.OUTGOING)
    private PeopleNode managedBy;

    private String role;

    @Relationship(type="REQUIRE", direction = Relationship.Direction.OUTGOING)
    private List<OpportunitySkillsRelationship> skills;


}
