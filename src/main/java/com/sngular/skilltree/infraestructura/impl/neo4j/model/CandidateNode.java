package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Node("Candidate")
@NoArgsConstructor
public class CandidateNode {

    @Id
    private String code;

    @Relationship(type = "CANDIDATE", direction = Relationship.Direction.OUTGOING)
    private PeopleNode candidate;

    @Relationship(type = "CANDIDATE_TO", direction = Relationship.Direction.OUTGOING)
    private OpportunityNode opportunity;

    @Relationship(type = "CANDIDATE_SKILL", direction = Relationship.Direction.OUTGOING)
    private List<SkillsCandidateRelationship> skills;

}
