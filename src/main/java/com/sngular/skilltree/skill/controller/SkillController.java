package com.sngular.skilltree.skill.controller;

import com.sngular.skilltree.api.SkillsApi;
import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.skill.mapper.SkillMapper;
import com.sngular.skilltree.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController implements SkillsApi {

    private final SkillService skillService;

    private final SkillMapper skillMapper;

    @Override
    public ResponseEntity<SkillDTO> getSkillByCode(String skillcode) {
        return ResponseEntity.ok(skillMapper
                .toSkillDTO(skillService
                        .findByCode(skillcode)));
    }

    @Override
    public ResponseEntity<List<SkillDTO>> getSKills() {
        var skillList = skillService.getAll();
        return ResponseEntity.ok(skillMapper.toSkillsDTO(skillList));
    }
}
