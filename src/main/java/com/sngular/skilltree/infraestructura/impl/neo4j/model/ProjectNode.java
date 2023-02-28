package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Node("Project")
@NoArgsConstructor
public class ProjectNode {

    /*@Id @GeneratedValue
    private Long id;*/

    @Id
    private String code;

    private String name;

    private String tag;

    private String desc;

    private String initDate;

    private String endDate;

    private String dominio;

    private String area;

    private String duration;

    private EnumGuards guards;

    @Relationship(type="REQUIRED", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> skills;

    @Relationship(type="PROJECT_FOR", direction = Relationship.Direction.OUTGOING)
    private ClientNode client;

    @Relationship(type="REQUIRE", direction = Relationship.Direction.INCOMING)
    private List<ProjectRoles> roles;
}
