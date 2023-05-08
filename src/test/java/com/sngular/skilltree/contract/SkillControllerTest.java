package com.sngular.skilltree.contract;

import static com.sngular.skilltree.fixtures.SkillFixtures.LIST_SKILL_JSON;
import static com.sngular.skilltree.fixtures.SkillFixtures.SKILL_BY_CODE;
import static com.sngular.skilltree.fixtures.SkillFixtures.SKILL_BY_CODE_JSON;
import static com.sngular.skilltree.fixtures.SkillFixtures.SKILL_LIST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.application.OfficeService;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.application.ProjectService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@WebMvcTest(controllers = SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SkillService skillService;

    @Test
    void getSkillByCode() throws Exception {
        when(skillService.findByCode(anyString())).thenReturn(SKILL_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                          .get("/skills/Spring")
                          .accept(MediaType.APPLICATION_JSON))
               .andExpect(content().json(SKILL_BY_CODE_JSON));
    }

    @Test
    void getSkills() throws Exception {
        when(skillService.getAll()).thenReturn(SKILL_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/skills")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LIST_SKILL_JSON));
    }

    @TestConfiguration
    static class OpportunityControllerTestConfiguration {

        @Bean
        SkillMapper skillMapper() {
            return Mappers.getMapper(SkillMapper.class);
        }

        @MockBean
        SkillService skillService;

        @MockBean
        PositionService positionService;

        @MockBean
        PeopleService peopleService;

        @MockBean
        ProjectService projectService;

        @MockBean
        OfficeService officeService;

        @MockBean
        ClientService clientService;

        @Bean
        ResolveService resolveService(final SkillService skillService, final PositionService positionService,
                                      final PeopleService peopleService, final ProjectService projectService,
                                      final OfficeService officeService, final ClientService clientService) {
            return new ResolveService(skillService, positionService, peopleService, projectService, officeService, clientService);
        }
    }
}
