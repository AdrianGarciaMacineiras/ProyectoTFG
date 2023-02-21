package com.sngular.skilltree.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;


@Builder(toBuilder = true)
public record People(String code, String employeeId, String name, String surname, Date birthDate, EnumTitle title,
                     List<Role> roles, List<Knows> knows, List<Skill> work_with, List<Skill> master,
                     List<Participate> participate, List<Skill> interest, List<Certificate> certificates) {

}
