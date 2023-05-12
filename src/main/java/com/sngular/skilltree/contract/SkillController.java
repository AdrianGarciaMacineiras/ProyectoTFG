package com.sngular.skilltree.contract;

import java.util.List;

import com.sngular.skilltree.api.SkillsApi;
import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SkillController implements SkillsApi {

    private final SkillService skillService;

    private final SkillMapper skillMapper;

    @Override
    public ResponseEntity<SkillDTO> getSkillByCode(String skillcode) {
        return ResponseEntity.ok(skillMapper.toSkillDTO(skillService.findByCode(skillcode)));
    }

    @Override
    public ResponseEntity<List<SkillDTO>> getSKills() {
        var skillList = skillService.getAll();
        return ResponseEntity.ok(skillMapper.toSkillsDTO(skillList));
    }
}
