package com.sngular.skilltree.infraestructura.impl.neo4j.projection;

import com.sngular.skilltree.model.EnumCharge;

public interface MemberRelationshipProjection {

  String getId();

  EnumCharge getCharge();

  PeopleNodeProjection getPeople();

}
