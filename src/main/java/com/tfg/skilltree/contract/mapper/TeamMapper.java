package com.tfg.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.api.model.MemberDTO;
import com.tfg.skilltree.api.model.PatchedTeamDTO;
import com.tfg.skilltree.api.model.TeamDTO;
import com.tfg.skilltree.application.ResolveService;
import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.model.Member;
import com.tfg.skilltree.model.Team;
import org.mapstruct.*;

@Mapper(config = CommonMapperConfiguration.class, uses = {PeopleMapper.class, ResolveService.class})
public interface TeamMapper {

    TeamDTO toTeamDTO(Team team);

    @AfterMapping
    default void setTags(Team team, @MappingTarget TeamDTO teamDto) {
        teamDto.setTags(team.tags());
    }

    List<MemberDTO> toMember(List<Member> members);

    default MemberDTO.Charge toCharge(String charge) {
        if (Objects.isNull(charge)) {
            charge = "unknown";
        }
        return MemberDTO.Charge.valueOf(charge.toUpperCase());
    }

    @Mapping(source = "people.employeeId", target = "people")
    MemberDTO toMemberDTO(Member members);

    @Mapping(source = "people", target = "people", qualifiedByName = {"resolveCodePeople" })
    Member toMember(MemberDTO membersDTO);

    Team toTeam(TeamDTO teamDTO);

    List<TeamDTO> toTeamsDTO(Collection<Team> teams);

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
