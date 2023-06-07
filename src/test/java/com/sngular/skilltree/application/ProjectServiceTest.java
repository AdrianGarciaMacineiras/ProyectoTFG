package com.sngular.skilltree.application;

import static com.sngular.skilltree.application.ProjectFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sngular.skilltree.application.implement.ProjectServiceImpl;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {projectService = new ProjectServiceImpl(projectRepository);}

    @Test
    @DisplayName("Testing save project")
    void testSave(){
        when(projectRepository.save(PROJECT_BY_CODE)).thenReturn(PROJECT_BY_CODE);
        Project result = projectService.create(PROJECT_BY_CODE);
        assertThat(result).isEqualTo(PROJECT_BY_CODE);
    }

    @Test
    @DisplayName("Test save exception")
    void testSaveException(){
        when(projectRepository.findByCode(anyString())).thenReturn(PROJECT_BY_CODE);
        Assertions.assertThrows(EntityFoundException.class, () ->
                projectService.create(PROJECT_BY_CODE)
        );
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
    void testFindByCode() {
        when(projectRepository.findByCode(anyString())).thenReturn(PROJECT_BY_CODE);
        Project result = projectService.findByCode("1");
        assertThat(result).isEqualTo(PROJECT_BY_CODE);
    }

    @Test
    @DisplayName("Test find project by code exception null")
    void testFindByCodeExceptionNull(){
        when(projectRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                projectService.findByCode("1")
        );
    }
    @Test
    @DisplayName("Test find project by code exception deleted")
    void testFindByCodeExceptionDeleted(){
        when(projectRepository.findByCode(anyString())).thenReturn(PROJECT_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                projectService.findByCode("1")
        );
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode() {
        when(projectRepository.deleteByCode(anyString())).thenReturn(true);
        when(projectRepository.findByCode("1")).thenReturn(PROJECT_BY_CODE);
        boolean result = projectService.deleteByCode("1");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Testing delete project exception is deleted")
    void testDeleteByCodeExceptionDeleteTrue(){
        when(projectRepository.findByCode(anyString())).thenReturn(PROJECT_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                projectService.deleteByCode("1")
        );
    }

    @Test
    @DisplayName("Testing delete project exception is null")
    void testDeleteByCodeExceptionNull(){
        when(projectRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                projectService.deleteByCode("1")
        );
    }

    @Test
    @DisplayName("Testing find project")
    void testFindProject() {
        when(projectRepository.findProject(anyString())).thenReturn(PROJECT_BY_CODE);
        Project result = projectService.findProject("1");
        assertThat(result).isEqualTo(PROJECT_BY_CODE);
    }

    @Test
    @DisplayName("Test find project exception null")
    void testFindProjectExceptionNull(){
        when(projectRepository.findProject(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                projectService.findProject("1")
        );
    }
    @Test
    @DisplayName("Test find project exception deleted")
    void testFindProjectExceptionDeleted(){
        when(projectRepository.findProject(anyString())).thenReturn(PROJECT_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                projectService.findProject("1")
        );
    }
}
