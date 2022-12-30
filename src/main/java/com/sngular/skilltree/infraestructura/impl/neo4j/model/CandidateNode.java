package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class CandidateNode {

    @Id
    private Long id;

    private @Setter(AccessLevel.PROTECTED) String code;

    private OpportunityNode opportunity;

    List<SkillsCandidateRelationship> skills;

}
