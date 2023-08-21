package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Node("People")
@NoArgsConstructor
@AllArgsConstructor
public class PeopleNode {

    @Id
    private String code;

    private String name;

    private String employeeId;

    private String surname;

    /*@ConvertWith(converter = LocalDateTimeConverter.class)
    private LocalDateTime birthDate;*/

    private String title;

    private boolean deleted;

    private boolean assignable;

    @Builder.Default
    @Relationship(type = "EVOLVED", direction = Relationship.Direction.OUTGOING)
    private List<Role> roles = new ArrayList<>();

    @Builder.Default
    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private List<KnowsRelationship> knows = new ArrayList<>();

    @Builder.Default
    @Relationship(type = "WORK_WITH", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> workWith = new ArrayList<>();

    @Builder.Default
    @Relationship(type = "MASTER", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> master = new ArrayList<>();

    @Builder.Default
    @Relationship(type = "IS_INTERESTED", direction = Relationship.Direction.OUTGOING)
    private List<SkillNode> interest = new ArrayList<>();

    @Builder.Default
    @Relationship(type = "HAS_CERTIFICATE", direction = Relationship.Direction.OUTGOING)
    private List<CertificateRelationship> certificates = new ArrayList<>();

    @Builder.Default
    @Relationship(type = "COVER", direction = Relationship.Direction.OUTGOING)
    private List<AssignedRelationship> assigns = new ArrayList<>();

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
