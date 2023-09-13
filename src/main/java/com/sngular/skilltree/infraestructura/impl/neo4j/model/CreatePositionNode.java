package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.EnumModeConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.ManagerView;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleView;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.ProjectView;
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
public class CreatePositionNode extends PositionAbstractNode {

    @Relationship(type = "FOR_PROJECT", direction = Relationship.Direction.OUTGOING)
    private ProjectView project;

    /*@Relationship(type = "IN", direction = Relationship.Direction.OUTGOING)
    private OfficeNode office;*/

    @Relationship(type = "MANAGED", direction = Relationship.Direction.INCOMING)
    private ManagerView managedBy;

}