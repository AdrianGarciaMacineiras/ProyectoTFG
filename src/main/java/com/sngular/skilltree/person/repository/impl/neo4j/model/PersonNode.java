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

    private Evolution evolution;

    private Knows knows;

    private List<String> work_with;

    private List<String> master;

    private Participate participate;

    private List<String> interest;
}
