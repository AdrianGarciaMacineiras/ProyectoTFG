package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Node("People")
@NoArgsConstructor
public class PeopleNode {

    @Id
    private Long id;

    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private String employeeId;

    private String surname;

    private Date birthDate;

    private EnumTitle enumTitle;

    private List<Evolution> evolution;

    private List<KnowsRelationship> knows;

    private List<SkillNode> work_with;

    private List<SkillNode> master;

    private List<ParticipateRelationship> participate;

    private List<SkillNode> interest;

    private List<Certificate> certificates;
}
