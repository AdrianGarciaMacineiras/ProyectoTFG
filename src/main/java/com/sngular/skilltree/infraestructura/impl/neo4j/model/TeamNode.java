package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Getter
@Setter
@Node("Team")
@NoArgsConstructor
public class TeamNode {

    @Id
    private String code;

    private String name;

    private String desc;

    private List<String> tags;

    @Relationship(type = "MEMBER_OF", direction = Relationship.Direction.INCOMING)
    private List<MemberRelationship> members;

    @Relationship(type = "USE", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> uses;

    @Relationship(type = "STRATEGIC", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> strategics;

    private boolean deleted;

}
