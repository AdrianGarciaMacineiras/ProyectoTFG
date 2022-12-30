package com.sngular.skilltree.infraestructura.impl.neo4j.model;

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
    private Long id;

    private @Setter(AccessLevel.PROTECTED) String code;

    private String name;

    private String desc;

    private Date initDate;

    private Date endDate;

    private String domain;

    private String area;

    private String duration;

    private EnumGuards enumGuards;

    private List<SkillNode> skills;

    private ClientNode client;

    private List<ProjectRoles> roles;
}
