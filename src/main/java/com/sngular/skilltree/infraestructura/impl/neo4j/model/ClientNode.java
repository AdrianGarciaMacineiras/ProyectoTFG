package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Getter
@Setter
@Node("Client")
@NoArgsConstructor
public class ClientNode {

    /*@Id @GeneratedValue
    private Long id;*/

    @Id
    private String code;

    private String name;

    private String industry;

    private String country;

    private String principalOffice;

    private String hQ;

    @Relationship(type="CLIENT", direction = Relationship.Direction.INCOMING)
    private List<OfficeNode> offices;

}
