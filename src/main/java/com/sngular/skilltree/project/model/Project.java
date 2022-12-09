package com.sngular.skilltree.project.model;

import com.sngular.skilltree.client.model.Client;
import com.sngular.skilltree.person.model.Person;
import com.sngular.skilltree.skill.model.Skill;

import java.util.Date;
import java.util.List;


public record Project(String code, String name, String description, Date initDate, Date endDate, String domain,
                      String duration, Guards guards, List<Skill> techArray, List<Person> people,
                      List<String> historic, Client client) {

}
