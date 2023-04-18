package com.sngular.skilltree.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record People(Long code, String employeeId, String name, String surname, LocalDate birthDate, EnumTitle title,
                     List<Role> roles, List<Knows> knows, List<Skill> work_with, List<Skill> master,
                     List<Skill> interest, List<Certificate> certificates, List<Assignments> assigns, boolean deleted, boolean assignable) {
}
