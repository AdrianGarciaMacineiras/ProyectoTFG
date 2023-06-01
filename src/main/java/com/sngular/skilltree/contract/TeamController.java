package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.TeamApi;
import com.sngular.skilltree.api.model.MemberDTO;
import com.sngular.skilltree.api.model.PatchedTeamDTO;
import com.sngular.skilltree.api.model.TeamDTO;
import com.sngular.skilltree.application.TeamService;
import com.sngular.skilltree.application.updater.TeamUpdater;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.contract.mapper.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;

    private final TeamUpdater teamUpdater;

    private final TeamMapper teamMapper;

    private final PeopleMapper peopleMapper;

    @Override
    public ResponseEntity<List<TeamDTO>> getTeams(){
        var teamList = teamService.getAll();
        return ResponseEntity.ok(teamMapper.toTeamsDTO(teamList));
    }

    @Override
    public ResponseEntity<TeamDTO> addTeam(TeamDTO teamDTO) {
        return ResponseEntity.ok(teamMapper
                .toTeamDTO(teamService
                        .create(teamMapper
                                .toTeam(teamDTO))));
    }

    @Override
    public ResponseEntity<TeamDTO> getTeamByCode(String teamcode) {
        return ResponseEntity.ok(teamMapper
                .toTeamDTO(teamService
                        .findByCode(teamcode)));
    }

    @Override
    public ResponseEntity<Void> deleteTeam(String teamcode) {
        var result = teamService.deleteByCode(teamcode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<TeamDTO> updateTeam(String teamcode, TeamDTO teamDTO) {
        return ResponseEntity.ok(teamMapper
                .toTeamDTO(teamUpdater
                        .update(teamcode, teamMapper
                                .toTeam(teamDTO))));
    }

    @Override
    public ResponseEntity<TeamDTO> patchTeam(String teamcode, PatchedTeamDTO patchedTeamDTO) {
        return ResponseEntity.ok(teamMapper
                .toTeamDTO(teamUpdater
                        .patch(teamcode, teamMapper
                                .toTeam(patchedTeamDTO))));
    }

    @Override
    public ResponseEntity<List<MemberDTO>> getTeamMembersInfo(String teamcode){
        return ResponseEntity.ok(teamMapper
                .toMember(teamService
                        .getMembers(teamcode)));
    }

}
