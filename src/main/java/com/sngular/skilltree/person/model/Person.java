package com.sngular.skilltree.person.model;

import lombok.*;
import java.util.Date;
import java.util.List;

@Value
public class Person {

    String code;

    String name;

    String surname;

    Date birthDate;

    Title title;

    Evolution evolution;

    Knows knows;

    List<String> work_with;

    List<String> master;

    Participate participate;

    List<String> interest;
}
