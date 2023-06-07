package com.sngular.skilltree.infraestructura.impl.neo4j.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Getter
@Setter
@Node("Skill")
@NoArgsConstructor
public class SkillNode {

    @Id
    private String code;

    private String name;

    @Relationship(type = "REQUIRE", direction = Relationship.Direction.INCOMING)
    private List<SubSkillsRelationship> subSkills;
}