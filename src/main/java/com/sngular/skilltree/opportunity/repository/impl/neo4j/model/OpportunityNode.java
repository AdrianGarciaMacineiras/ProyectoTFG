package com.sngular.skilltree.opportunity.repository.impl.neo4j.model;

import com.sngular.skilltree.client.model.Client;
import com.sngular.skilltree.project.repository.impl.neo4j.model.ProjectNode;
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

    private Client client;

    private Date openingDate;

    private Date closingDate;

    private String priority;

    private Mode mode;

    private String office;

    private String role;

    private List<Skills> skills;


}
