package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Node
@Data
@NoArgsConstructor
public class CandidateNode {

    @Id
    private String code;

    private PeopleNode candidate;

    private OpportunityNode opportunity;

    private List<SkillsCandidateRelationship> skills;

}
