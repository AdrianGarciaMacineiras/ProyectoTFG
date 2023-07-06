package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.EnumModeConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Node("Position")
public class PositionNode {

    @Id
    private String code;

    private String name;

    private boolean deleted;

    private String active;

    @ConvertWith(converter = LocalDateConverter.class)
    @Property("initDate")
    private LocalDate openingDate;

    @ConvertWith(converter = LocalDateConverter.class)
    @Property("endDate")
    private LocalDate closingDate;

    private String priority;

    @ConvertWith(converter = EnumModeConverter.class)
    private EnumMode mode;

    @Property("charge")
    private String role;

    @Relationship(type="FOR_PROJECT", direction = Relationship.Direction.OUTGOING)
    private ProjectNode project;

    @Relationship(type = "IN", direction = Relationship.Direction.OUTGOING)
    private OfficeNode office;

    @Relationship(type = "MANAGED", direction = Relationship.Direction.INCOMING)
    private PeopleNode managedBy;

    @Relationship(type = "NEED", direction = Relationship.Direction.OUTGOING)
    private List<PositionSkillsRelationship> skills;

    @Relationship(type = "CANDIDATE", direction = Relationship.Direction.OUTGOING)
    private List<CandidateRelationship> candidates;

}
