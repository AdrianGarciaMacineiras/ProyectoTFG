package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Getter
@Setter
@Builder
@Node("Skill")
@NoArgsConstructor
@AllArgsConstructor
public class SkillNode {

    @Id
    private String code;

    private String name;

    private String namespace;

    @Relationship(type = "REQUIRE", direction = Relationship.Direction.INCOMING)
    private List<SubSkillsRelationship> subSkills;
}