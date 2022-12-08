package com.sngular.skilltree.person.model;

import java.util.Date;
import java.util.List;


public record Person(String code, String name, String surname, Date birthDate, Title title, Evolution evolution,
                     Knows knows, List<String> work_with, List<String> master, Participate participate,
                     List<String> interest) {

}
