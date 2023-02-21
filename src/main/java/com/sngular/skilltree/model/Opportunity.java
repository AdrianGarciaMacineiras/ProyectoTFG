package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;
import java.util.List;


@Builder(toBuilder = true)
public record Opportunity(String code, String name, Project project, Client client, Date openingDate, Date closingDate,
                          String priority, EnumMode mode, Office office, String role, List<OpportunitySkill> skills,
                          People managedBy) {

}
