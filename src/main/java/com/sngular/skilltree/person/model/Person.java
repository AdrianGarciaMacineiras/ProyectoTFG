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

    private enum Title{
        PRINCIPAL,
        HEAD,
        DIRECTOR,
        EXPERT,
        SENIOR,
        DEVELOPER,
        JUNIOR
    }

    Evolution evolution;

    class Evolution{
         String title;
         String category;
         Date fromDate;
    };

    Knows knows;

    private class Knows{
        String code;
        String level;
    };

    List<String> work_with;

    List<String> master;

    Participate participate;

    private class Participate{
        String code;
        List<Roles> roles;

        private class Roles{
            String role;
            Date fromDate;
            Date enDate;
        };
    }

    List<String> interest;
}
