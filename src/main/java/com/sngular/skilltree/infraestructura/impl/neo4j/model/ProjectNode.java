package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDate;
import java.util.List;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.EnumGuardConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Getter
@Setter
@Node("Project")
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectNode {

    @Id
    private String code;

    private String name;

    private String tag;

    private String desc;

    @ConvertWith(converter = LocalDateConverter.class)
    private LocalDate initDate;

    @ConvertWith(converter = LocalDateConverter.class)
    private LocalDate endDate;

    private String domain;

    private String area;

    private String duration;

    @ConvertWith(converter = EnumGuardConverter.class)
    private EnumGuards guards;

    private boolean deleted;

    @Relationship(type="REQUIRE", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> skills;

    @Relationship(type = "FOR_CLIENT", direction = Relationship.Direction.OUTGOING)
    private ClientNode client;
}
