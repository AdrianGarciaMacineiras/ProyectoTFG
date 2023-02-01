package com.sngular.skilltree.project.controller;

import com.sngular.skilltree.application.ProjectService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.contract.ProjectController;
import com.sngular.skilltree.contract.mapper.ProjectMapper;
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

import static com.sngular.skilltree.project.fixtures.ProjectFixtures.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void getProjectByCode() throws Exception {
        when(projectService.findByCode(anyString())).thenReturn(PROJECT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/project/cosmosdata")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PROJECT_BY_CODE_JSON));
    }

    @TestConfiguration
    static class ControllerTestConfiguration {

        @Bean
        public ProjectMapper getProjectMapper() {
            return Mappers.getMapper(ProjectMapper.class);
        }

        @Bean
        public SkillMapper getSkillMapper() {
            return Mappers.getMapper(SkillMapper.class);
        }

        @MockBean
        public ResolveService resolveService;
    }
}
