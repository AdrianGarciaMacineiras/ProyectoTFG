package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record People(String code, String employeeId, String name, String surname, LocalDate birthDate, String title,
                     List<Role> roles, List<Knows> knows, List<Skill> work_with, List<Skill> master,
                     List<Skill> interest, List<Certificate> certificates, List<Assignments> assigns,
                     List<Candidate> candidacies,
                     boolean deleted, boolean assignable, List<String> noClients, List<String> noProjects) {
}
