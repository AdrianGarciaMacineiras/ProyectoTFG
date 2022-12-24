package com.sngular.skilltree.model;

import java.util.Date;
import java.util.List;


public record Project(String code, String name, String desc, Date initDate, Date endDate, String domain,
                      String duration, EnumGuards enumGuards, List<Skill> skills, List<String> historic,
                      Client client, String area, List<ProjectRoles> roles) {
}
