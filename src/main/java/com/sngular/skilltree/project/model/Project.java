package com.sngular.skilltree.project.model;

import com.sngular.skilltree.person.model.Person;
import lombok.Value;
import java.util.Date;
import java.util.List;

@Value
public class Project {

    String code;

    String name;

    String description;

    Date initDate;

    Date endDate;

    String domain;

    String duration;

    Guards guards;

    List<Tecnologia> techArray;

    List<Person> people;

    List<String> historic;

}
