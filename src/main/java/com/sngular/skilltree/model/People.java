package com.sngular.skilltree.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record People(String code, String employeeId, String name, String surname, LocalDate birthDate, String title,
                     List<Role> roles, List<Knows> knows, List<Skill> workWith, List<Skill> master,
                     List<Skill> interest, List<Certificate> certificates, List<Assignments> assigns,
                     List<Candidate> candidacies,
                     boolean deleted, boolean assignable, List<String> noClients, List<String> noProjects) {
}
