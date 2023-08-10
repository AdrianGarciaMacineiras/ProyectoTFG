package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.SkillApi;
import com.sngular.skilltree.api.SkillsApi;
import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.api.model.SkillStatDTO;
import com.sngular.skilltree.api.model.StrategicTeamSkillDTO;
import com.sngular.skilltree.api.model.StrategicTeamSkillNotUsedDTO;
import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController implements SkillsApi, SkillApi {

  private final SkillService skillService;

  private final SkillMapper skillMapper;

  @Override
  public ResponseEntity<SkillDTO> getSkillByCode(String skillcode) {
    return ResponseEntity.ok(skillMapper
                               .toSkillDTO(skillService
                                             .findByCode(skillcode)));
  }

    @Override
    public ResponseEntity<List<SkillDTO>> getSkills() {
        var skillList = skillService.getAll();
        return ResponseEntity.ok(skillMapper.toSkillsDTO(skillList));
    }

    @Override
    public  ResponseEntity<List<StrategicTeamSkillDTO>> getStrategicSkillsUse(){
        return ResponseEntity.ok(skillMapper
                .toStrategicTeamSkillDTO(skillService
                        .getStrategicSkillsUse()));
    }

    @Override
    public  ResponseEntity<List<StrategicTeamSkillNotUsedDTO>> getNoStrategicSkillsUse(){
        return ResponseEntity.ok(skillMapper
                .toStrategicTeamSkillNotUsed(skillService
                        .getNoStrategicSkillsUse()));
    }

    @Override
    public ResponseEntity<SkillStatDTO> getSkillStatsByTittle(String tittle) {
        return ResponseEntity.ok(skillMapper.toSkillStat(skillService.getSkillStatsByTittle(tittle)));
    }
}
