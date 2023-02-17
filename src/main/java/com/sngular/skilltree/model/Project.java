package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;
import java.util.List;


@Builder(toBuilder = true)
public record Project(String code, String tag, String name, String desc, Date initDate, Date endDate, String domain,
                      String duration, EnumGuards guards, List<Skill> skills, List<String> historic,
                      Client client, String area, List<ProjectRoles> roles) {
}
