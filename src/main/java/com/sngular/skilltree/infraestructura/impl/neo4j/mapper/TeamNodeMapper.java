package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.TeamNode;
import com.sngular.skilltree.model.Team;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PeopleNodeMapper.class)
public interface TeamNodeMapper {

    TeamNode toNode (Team team);

    Team fromNode(TeamNode teamNode);

    List<Team> map(List<TeamNode> all);
}
