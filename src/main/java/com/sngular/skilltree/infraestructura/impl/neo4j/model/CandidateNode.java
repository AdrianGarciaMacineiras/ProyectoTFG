package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Getter
@Setter
@Node("Candidate")
@NoArgsConstructor
public class CandidateNode {

    @Id
    private String code;

    private EnumStatus status;

    @Relationship(type = "CANDIDATE", direction = Relationship.Direction.OUTGOING)
    private PeopleNode candidate;

    @Relationship(type = "CANDIDATE_TO", direction = Relationship.Direction.OUTGOING)
    private OpportunityNode opportunity;

    private boolean deleted;

}
