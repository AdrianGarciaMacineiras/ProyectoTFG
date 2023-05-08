package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.SkillFixtures.SKILL3_BY_CODE;
import static com.sngular.skilltree.application.SkillFixtures.SKILL_BY_CODE;
import static com.sngular.skilltree.application.SkillFixtures.SKILL_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.implement.SkillServiceImpl;
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

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    private SkillService skillService;

    private final SkillMapper mapper = Mappers.getMapper(SkillMapper.class);

    @BeforeEach
    void setUp() {
        skillService = new SkillServiceImpl(skillRepository);
    }

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
