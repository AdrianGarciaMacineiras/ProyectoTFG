package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceTeamNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.TeamNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.projection.MemberRelationshipProjection;
import com.sngular.skilltree.infraestructura.impl.neo4j.projection.TeamProjection;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.TeamView;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {PeopleNodeMapper.class, SkillNodeMapper.class, ResolveServiceTeamNode.class})
public interface TeamNodeMapper {

  @Mapping(target = "members", source = "members", qualifiedByName = {"resolveServiceTeamNode", "mapToMemberRelationship"})
  TeamNode toNode(Team team);

  @Mapping(target = "members", source = "members", qualifiedByName = {"resolveServiceTeamNode", "mapToMember"})
  Team fromNode(TeamNode teamNode);

  Team map(TeamView teamView);

  List<Team> mapProjection(List<TeamProjection> all);

  List<Member> mapMemberRelationshipProjection(List<MemberRelationshipProjection> memberRelationshipProjections);

  List<Team> map(List<TeamView> all);

  List<Team> mapNode(List<TeamNode> all);

}
