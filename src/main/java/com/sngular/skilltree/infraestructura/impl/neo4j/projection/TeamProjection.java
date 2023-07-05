package com.sngular.skilltree.infraestructura.impl.neo4j.projection;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;

import java.util.List;

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
