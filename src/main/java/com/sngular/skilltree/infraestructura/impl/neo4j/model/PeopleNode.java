package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Node("People")
@NoArgsConstructor
public class PeopleNode {

    @Id
    private String code;

    private String name;

    private String employeeId;

    private String surname;

    @ConvertWith(converter = LocalDateConverter.class)
    private LocalDate birthDate;

    private String title;

    private boolean deleted;

    private boolean assignable;

    @Relationship(type="EVOLVED", direction = Relationship.Direction.OUTGOING)
    private List<Role> roles;

    @Relationship(type = "KNOW", direction = Relationship.Direction.OUTGOING)
    private List<KnowsRelationship> knows;

    @Relationship(type="WORK_WITH", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> work_with;

    @Relationship(type="MASTER", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> master;

    @Relationship(type="IS_INTERESTED", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> interest;

    @Relationship(type = "HAVE_CERTIFICATE", direction = Relationship.Direction.OUTGOING)
    private List<CertificateRelationship> certificates;

    @Relationship(type = "COVER", direction = Relationship.Direction.OUTGOING)
    private List<AssignedRelationship> assigns;

    /*@Relationship(type="REFUSE_CLIENT", direction = Relationship.Direction.INCOMING)
    private List<ClientNode> noClients;

    @Relationship(type="REFUSE_PROJECT", direction = Relationship.Direction.INCOMING)
    private List<ProjectNode> noProjects;

    @Relationship(type="MENTOR", direction = Relationship.Direction.INCOMING)
    private List<PeopleNode> mentor;

    private boolean quemada;

    private boolean estancada;
    */
}
