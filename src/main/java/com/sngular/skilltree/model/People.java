package com.sngular.skilltree.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;


@Builder
public record People(String code, String name, String surname, Date birthDate, EnumTitle enumTitle, List<Evolution> evolution,
                     List<Knows> knows, List<Skill> work_with, List<Skill> master, List<Participate> participate,
                     List<Skill> interest) {

}
