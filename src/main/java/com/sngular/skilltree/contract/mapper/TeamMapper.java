package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.api.model.*;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicUse;
import com.sngular.skilltree.model.Team;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = CommonMapperConfiguration.class, uses = {PeopleMapper.class, ResolveService.class})
public interface TeamMapper {

    TeamDTO toTeamDTO(Team team);

    @AfterMapping
    default void setTags(Team team, @MappingTarget TeamDTO teamDto) {
        teamDto.setTags( team.tags() );
    }

    List<MembersDTO> toMember(List<Member> members);

    @Mapping(source="people.code", target = "peopleCode")
    MembersDTO toMemberDTO(Member members);

    @Mapping(target = "people", source = "peopleCode", qualifiedByName = {"resolveCodePeople"})
    Member toMember(MembersDTO membersDTO);

    Team toTeam(TeamDTO teamDTO);

    List<TeamDTO> toTeamsDTO (Collection<Team> teams);

    Team toTeam(PatchedTeamDTO patchedTeamDTO);

    @Named("patch")
    default Team patch(Team newTeam, Team oldTeam) {
        Team.TeamBuilder teamBuilder = oldTeam.toBuilder();

        return teamBuilder
                .code(oldTeam.code())
                .members((Objects.isNull(newTeam.members())) ? oldTeam.members() : newTeam.members())
                .description((Objects.isNull(newTeam.description())) ? oldTeam.description() : newTeam.description())
                .name((Objects.isNull(newTeam.name())) ? oldTeam.name() : newTeam.name())
                .tags((Objects.isNull(newTeam.tags())) ? oldTeam.tags() : newTeam.tags())
                .build();

    }
}
