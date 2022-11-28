package com.sngular.skilltree.person.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private String surname;

    private Date birthDate;

    private Title title;

    private enum Title{
        PRINCIPAL,
        HEAD,
        DIRECTOR,
        EXPERT,
        SENIOR,
        DEVELOPER,
        JUNIOR
    }

    private class evolution{
         String title;
         String category;
         Date fromDate;
    };

    private class knows{
        String code;
        String level;
    };

    private List<String> work_with;

    private List<String> master;

    private class participate{
        String code;
        List<Roles> roles;

        private class Roles{
            String role;
            Date fromDate;
            Date enDate;
        };
    }

    private List<String> interest;
}
