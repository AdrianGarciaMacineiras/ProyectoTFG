package com.sngular.skilltree.person.repository.impl.neo4j.model;

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
public class PersonNode {

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

    private Evolution evolution;

    class Evolution{
        String title;
        String category;
        Date fromDate;
    };

    private Knows knows;

    private class Knows{
        String code;
        String level;
    };

    List<String> work_with;

    List<String> master;

    private Participate participate;

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
