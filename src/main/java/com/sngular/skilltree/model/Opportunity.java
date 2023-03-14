package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Builder(toBuilder = true)
public record Opportunity(String code, String name, Project project, Client client, LocalDate openingDate, LocalDate closingDate,
                          String priority, EnumMode mode, Office office, String role, List<OpportunitySkill> skills,
                          List<Candidate> candidates, People managedBy, boolean deleted) {

}
