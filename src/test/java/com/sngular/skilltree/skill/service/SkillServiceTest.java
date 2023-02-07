package com.sngular.skilltree.skill.service;

import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.application.SkillServiceImpl;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.infraestructura.SkillRepository;
import com.sngular.skilltree.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.sngular.skilltree.skill.service.SkillFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    private SkillService skillService;

    private SkillMapper mapper = Mappers.getMapper(SkillMapper.class);

    @BeforeEach
    void setUp(){skillService = new SkillServiceImpl(skillRepository);}

    @Test
    @DisplayName("Testing getAll the skills")
    void testGetAll(){
        when(skillRepository.findAll()).thenReturn(SKILL_LIST);
        List<Skill> result = skillService.getAll();
        assertThat(result).containsExactly(SKILL_BY_CODE, SKILL3_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a skill")
    void testFindByCode(){
        when(skillRepository.findByCode(anyString())).thenReturn(SKILL_BY_CODE);
        Skill result = skillService.findByCode("s1120");
        assertThat(result).isEqualTo(SKILL_BY_CODE);
    }
}
