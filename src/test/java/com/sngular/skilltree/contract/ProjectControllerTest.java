package com.sngular.skilltree.contract;

import com.sngular.skilltree.CommonTestConfiguration;
import com.sngular.skilltree.application.*;
import com.sngular.skilltree.application.updater.ProjectUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.ProjectMapper;
import com.sngular.skilltree.contract.mapper.ProjectMapperImpl;
import com.sngular.skilltree.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sngular.skilltree.fixtures.ProjectFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = ProjectController.class)
@Import(CommonTestConfiguration.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectUpdater projectUpdater;

    @Autowired
    private ProjectService projectService;

    @Test
    void getProjectByCode() throws Exception {
        when(projectService.findByCode(anyString())).thenReturn(PROJECT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/project/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PROJECT_BY_CODE_JSON));
    }
    @Test
    void shouldDeleteProjectBySuccess() throws Exception{
        when(projectService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/project/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteProjectFail() throws Exception{
        when(projectService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Project", "cosmosdata"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/project/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProject() throws Exception {
        when(projectUpdater.update(anyString(), any(Project.class))).thenReturn(UPDATED_PROJECT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(UPDATED_PROJECT_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(UPDATED_PROJECT_BY_CODE_JSON));
    }

    @Test
    void addProject() throws Exception {
        when(projectService.create(any(Project.class))).thenReturn(PROJECT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PROJECT_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(PROJECT_BY_CODE_JSON));
    }

    @Test
    void patchProject() throws Exception {
        when(projectUpdater.patch(anyString(), any(Project.class))).thenReturn(UPDATED_PROJECT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/project/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PATCH_PROJECT_BY_CODE_JSON))
                .andExpect(content().json(PATCH_PROJECT_BY_CODE_JSON));
    }

    @Test
    void getProjects() throws Exception {
        when(projectService.getAll()).thenReturn(PROJECT_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/project")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LIST_PROJECT_JSON));
    }

    @TestConfiguration
    static class ControllerTestConfiguration {

        @Bean
        public ProjectMapper getProjectMapper(final ResolveService resolveService) {
            return new ProjectMapperImpl(resolveService);
        }

        @MockBean
        ProjectUpdater projectUpdater;

        @MockBean
        ProjectService projectService;

        @MockBean
        SkillService skillService;

        @MockBean
        PositionService positionService;

        @MockBean
        PeopleService peopleService;

        @MockBean
        OfficeService officeService;

        @MockBean
        ClientService clientService;

        @Bean
        ResolveService resolveService(
          final SkillService skillService, final PositionService positionService,
          final PeopleService peopleService, final ProjectService projectService,
          final OfficeService officeService, final ClientService clientService) {
            return new ResolveService(skillService, positionService, peopleService, projectService, officeService, clientService);
        }
    }
}
