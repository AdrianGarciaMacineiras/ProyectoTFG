package com.sngular.skilltree.opportunity.model;

import com.sngular.skilltree.client.model.Client;
import com.sngular.skilltree.project.model.Project;
import lombok.Value;
import java.util.Date;
import java.util.List;


@Value
public class Opportunity {

    String code;

    String name;

    Project project;

    Client client;

    Date openingDate;

    Date closingDate;

    String priority;

    Mode mode;

    String office;

    String role;

    List<Skill> skills;


}
