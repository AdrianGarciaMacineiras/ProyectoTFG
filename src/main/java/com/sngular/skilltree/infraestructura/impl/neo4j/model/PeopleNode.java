package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Node("People")
@NoArgsConstructor
public class PeopleNode {

    @Id
    private Integer code;

    private String name;

    private Integer employeeId;

    private String surname;

    private LocalDate birthDate;

    private EnumTitle enumTitle;

    private boolean deleted;

    @Relationship(type="EVOLVED", direction = Relationship.Direction.OUTGOING)
    private List<Role> roles;

    @Relationship(type="KNOWS", direction = Relationship.Direction.OUTGOING)
    private List<KnowsRelationship> knows;

    @Relationship(type="WORK_WITH", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> work_with;

    @Relationship(type="MASTER", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> master;

    @Relationship(type="PARTICIPATE", direction = Relationship.Direction.OUTGOING)
    private List<ParticipateRelationship> participate;

    @Relationship(type="IS_INTERESTED", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> interest;

    @Relationship(type="HAS_CERTIFICATE", direction = Relationship.Direction.OUTGOING)
    private List<CertificateRelationship> certificates;
}
