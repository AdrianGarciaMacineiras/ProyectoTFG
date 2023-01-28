package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.PatchedTeamDTO;
import com.sngular.skilltree.model.Team;
import com.sngular.skilltree.api.model.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = PeopleMapper.class)
public interface TeamMapper {

    TeamDTO toTeamDTO(Team team);

    Team toTeam(TeamDTO teamDTO);

    List<TeamDTO> toTeamsDTO (Collection<Team> teams);

    Team toTeam(PatchedTeamDTO patchedTeamDTO);

    void update(@MappingTarget Team oldTeam, Team newTeam);
}
