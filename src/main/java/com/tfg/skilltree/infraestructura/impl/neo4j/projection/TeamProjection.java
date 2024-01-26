package com.tfg.skilltree.infraestructura.impl.neo4j.projection;

import java.util.List;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.SkillNode;

public interface TeamProjection {

  String getCode();

  String getName();

  String getDesc();

  List<String> getTags();

  List<MemberRelationshipProjection> getMembers();

  List<SkillNode> getUses();

  List<SkillNode> getStrategics();

  boolean getDeleted();


}
