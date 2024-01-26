package com.tfg.skilltree.infraestructura.impl.neo4j.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Getter
@Setter
@Node("Client")
@NoArgsConstructor
public class ClientNode {

    @Id
    private String code;

    private String name;

    private String industry;

    private String country;

    /*@Relationship(type = "BELONG", direction = Relationship.Direction.INCOMING)
    private List<OfficeNode> offices;*/

    private boolean deleted;

}
