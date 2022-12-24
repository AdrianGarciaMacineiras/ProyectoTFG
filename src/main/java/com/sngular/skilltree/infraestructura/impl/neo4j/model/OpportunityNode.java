package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class OpportunityNode {

    @Id
    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private ProjectNode project;

    private ClientNode client;

    private Date openingDate;

    private Date closingDate;

    private String priority;

    private EnumMode enumMode;

    private String office;

    private String role;

    private List<SkillsRelationship> skills;


}
