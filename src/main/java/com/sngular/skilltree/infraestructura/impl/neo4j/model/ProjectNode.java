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

    @Id
    private Long id;

    private String code;

    private String name;

    private String tag;

    private String desc;

    private Date initDate;

    private Date endDate;

    private String domain;

    private String area;

    private String duration;

    private EnumGuards enumGuards;

    @Relationship(type="REQUIRED", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> skills;

    @Relationship(type="PROJECT_FOR", direction = Relationship.Direction.INCOMING)
    private ClientNode client;

    @Relationship(type="REQUIRE", direction = Relationship.Direction.INCOMING)
    private List<ProjectRoles> roles;
}
