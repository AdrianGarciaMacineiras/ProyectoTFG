package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Getter
@Setter
@Node("Client")
@NoArgsConstructor
public class ClientNode {

    @Id
    private Long id;

    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private String industry;

    private String country;

    private String principalOffice;

    private String HQ;

    private List<Office> offices;

}
