package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDate;
import java.util.List;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.EnumModeConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.ManagerView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Getter
@Setter
@NoArgsConstructor
public class PositionNode extends PositionAbstractNode{

    @Relationship(type = "FOR_PROJECT", direction = Relationship.Direction.OUTGOING)
    private ProjectNode project;

    /*@Relationship(type = "IN", direction = Relationship.Direction.OUTGOING)
    private OfficeNode office;*/

    @Relationship(type = "MANAGED", direction = Relationship.Direction.INCOMING)
    private PeopleNode managedBy;

}
