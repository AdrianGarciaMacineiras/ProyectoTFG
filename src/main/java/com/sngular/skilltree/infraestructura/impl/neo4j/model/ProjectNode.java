package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Node("Project")
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectNode {

    @Id
    private Long code;

    private String name;

    private String tag;

    private String desc;

    private LocalDate initDate;

    private LocalDate endDate;

    private String dominio;

    private String area;

    private String duration;

    private EnumGuards guards;

    private boolean deleted;

    @Relationship(type="REQUIRED", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> skills;

    @Relationship(type="PROJECT_FOR", direction = Relationship.Direction.OUTGOING)
    private ClientNode client;

    @Relationship(type="REQUIRE", direction = Relationship.Direction.INCOMING)
    private List<ProjectRoles> roles;
}
