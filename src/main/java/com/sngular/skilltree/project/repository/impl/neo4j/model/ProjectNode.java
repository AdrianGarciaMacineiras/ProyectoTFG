package com.sngular.skilltree.project.repository.impl.neo4j.model;

import com.sngular.skilltree.person.model.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class ProjectNode {


    @Id
    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private String description;

    private Date initDate;

    private Date endDate;

    private String domain;

    private String duration;

    private Guards guards;

    private List<Tecnologia> techArray;

    private List<Person> people;

    private List<String> historic;
}
