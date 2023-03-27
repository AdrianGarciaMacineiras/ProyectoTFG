package com.sngular.skilltree.project.service;

import com.sngular.skilltree.application.ProjectService;
import com.sngular.skilltree.application.implement.ProjectServiceImpl;
import com.sngular.skilltree.contract.mapper.ProjectMapper;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.sngular.skilltree.project.service.ProjectFixtures.*;
import static com.sngular.skilltree.project.service.ProjectFixtures.PROJECT_BY_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    private ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    @BeforeEach
    void setUp(){projectService = new ProjectServiceImpl(projectRepository);}

    @Test
    @DisplayName("Testing save project")
    void testSave(){
        when(projectRepository.save(PROJECT_BY_CODE)).thenReturn(PROJECT_BY_CODE);
        Project result = projectService.create(PROJECT_BY_CODE);
        assertThat(result).isEqualTo(PROJECT_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the projects")
    void testGetAll(){
        when(projectRepository.findAll()).thenReturn(PROJECT_LIST);
        List<Project> result = projectService.getAll();
        assertThat(result).containsExactly(PROJECT_BY_CODE, PROJECT2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a project")
    void testFindByCode(){
        when(projectRepository.findByCode(anyLong())).thenReturn(PROJECT_BY_CODE);
        Project result = projectService.findByCode(1L);
        assertThat(result).isEqualTo(PROJECT_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(projectRepository.deleteByCode(anyLong())).thenReturn(true);
        when(projectRepository.findByCode(1L)).thenReturn(PROJECT_BY_CODE);
        boolean result = projectService.deleteByCode(1L);
        assertThat(result).isTrue();
    }

}
