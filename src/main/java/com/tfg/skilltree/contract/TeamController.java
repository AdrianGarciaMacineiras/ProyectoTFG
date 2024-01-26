package com.tfg.skilltree.contract;

import java.util.List;

import com.tfg.skilltree.api.TeamApi;
import com.tfg.skilltree.api.model.MemberDTO;
import com.tfg.skilltree.api.model.PatchedTeamDTO;
import com.tfg.skilltree.api.model.TeamDTO;
import com.tfg.skilltree.application.TeamService;
import com.tfg.skilltree.application.updater.TeamUpdater;
import com.tfg.skilltree.contract.mapper.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;

    private final TeamUpdater teamUpdater;

    private final TeamMapper teamMapper;

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

