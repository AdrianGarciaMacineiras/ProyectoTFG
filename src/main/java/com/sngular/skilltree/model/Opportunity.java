package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;
import java.util.List;


@Builder(toBuilder = true)
public record Opportunity(String code, String name, String project, String client, Date openingDate, Date closingDate,
                          String priority, EnumMode mode, String office, String role, List<OpportunitySkill> skills,
                          String managedBy) {

}
