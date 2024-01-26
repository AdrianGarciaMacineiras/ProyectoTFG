package com.tfg.skilltree.infraestructura.impl.neo4j.projection;

import com.tfg.skilltree.model.EnumCharge;

public interface MemberRelationshipProjection {

  String getId();

  EnumCharge getCharge();

  PeopleNodeProjection getPeople();

}
