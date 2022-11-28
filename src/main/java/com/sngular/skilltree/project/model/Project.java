package com.sngular.skilltree.project.model;

import com.sngular.skilltree.person.model.Person;
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
public class Project {

    @Id
    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private String description;

    private Date initDate;

    private Date endDate;

    private String domain;

    private String duration;

    private Guards guards;
    private enum Guards{
        PASSIVE,
        ACTIVE,
        NO_GUARD
    }

    private List<Tecnologia> techArray;

    private class Tecnologia{
        String code;
        String nombre;
    }
    private List<Person> people;

    private List<String> historic;

}
