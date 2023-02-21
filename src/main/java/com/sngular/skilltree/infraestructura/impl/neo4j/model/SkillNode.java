package com.sngular.skilltree.infraestructura.impl.neo4j.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Getter
@Setter
@Node("Skill")
@NoArgsConstructor
public class SkillNode {

    @Id @GeneratedValue
    private Long id;

    private String code;

    private String name;

    @Relationship(type="REQUIRE", direction = Relationship.Direction.INCOMING)
    private List<SkillNode> subSkills;
}