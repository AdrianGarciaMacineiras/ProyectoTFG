package com.sngular.skilltree.model;

import java.util.Date;
import java.util.List;


public record Opportunity(String code, String name, Project project, Client client, Date openingDate, Date closingDate,
                          String priority, EnumMode enumMode, String office, String role, List<Skills> skills) {

}
