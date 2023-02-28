package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@Node("Office")
@NoArgsConstructor
public class OfficeNode {

    /*@Id
    @GeneratedValue
    private Long id;*/

    @Id
    private String code;

    private String name;

    private String address;

    private String phone;

    private String geolocation;
}
