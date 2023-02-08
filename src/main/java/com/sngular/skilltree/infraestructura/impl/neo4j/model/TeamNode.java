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
public class TeamNode {

    @Id
    private String code;

    private String name;

    private String description;

    private List<String> tags;

    private List<MemberRelationship> members;

}
