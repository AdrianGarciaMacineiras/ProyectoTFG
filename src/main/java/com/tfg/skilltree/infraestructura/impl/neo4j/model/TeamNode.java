package com.tfg.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDateTime;
import java.util.List;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateTimeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Getter
@Setter
@Node("Team")
@NoArgsConstructor
public class TeamNode {

    @Id
    private String code;

    private String name;

    private String desc;

    private String shortName;

    private List<String> tags;

    @Relationship(type = "MEMBER_OF", direction = Relationship.Direction.INCOMING)
    private List<MemberRelationship> members;

    @Relationship(type = "USE", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> uses;

    @Relationship(type = "STRATEGIC", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> strategics;

    private boolean deleted;

    @LastModifiedDate
    @Property(name = "_lastUpdated", readOnly = true)
    @ConvertWith(converter = LocalDateTimeConverter.class)
    private LocalDateTime lastUpdated;


}