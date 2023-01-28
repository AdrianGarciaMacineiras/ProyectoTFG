package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.TeamApi;
import com.sngular.skilltree.api.model.PatchedTeamDTO;
import com.sngular.skilltree.application.TeamService;
import com.sngular.skilltree.contract.mapper.TeamMapper;
import com.sngular.skilltree.api.model.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;

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
                .toTeamDTO(teamService
                        .update(teamcode, teamMapper
                                .toTeam(teamDTO))));
    }

    @Override
    public ResponseEntity<TeamDTO> patchTeam(String teamcode, PatchedTeamDTO patchedTeamDTO) {
        return ResponseEntity.ok(teamMapper
                .toTeamDTO(teamService
                        .patch(teamcode, teamMapper
                                .toTeam(patchedTeamDTO))));
    }

}
