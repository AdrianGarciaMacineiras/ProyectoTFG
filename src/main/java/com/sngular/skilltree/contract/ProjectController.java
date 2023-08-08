package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.ProjectApi;
import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import com.sngular.skilltree.application.ProjectService;
import com.sngular.skilltree.application.updater.ProjectUpdater;
import com.sngular.skilltree.contract.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectApi {

  private final ProjectService projectService;

  private final ProjectUpdater projectUpdater;

  private final ProjectMapper projectMapper;

  @Override
  public ResponseEntity<ProjectDTO> getProjectByCode(String projectCode) {
    return ResponseEntity.ok(projectMapper
                               .toProjectDTO(projectService
                                               .findByCode(projectCode)));
  }

  @Override
  public ResponseEntity<Void> deleteProject(String projectCode) {
    var result = projectService.deleteByCode(projectCode);
    return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @Override
  public ResponseEntity<ProjectDTO> updateProject(String projectCode, ProjectDTO projectDTO) {
    return ResponseEntity.ok(projectMapper
                               .toProjectDTO(projectUpdater
                                               .update(projectCode, projectMapper
                                                 .toProject(projectDTO))));
  }

  @Override
  public ResponseEntity<ProjectDTO> patchProject(String projectCode, PatchedProjectDTO patchedProjectDTO) {
    return ResponseEntity.ok(projectMapper
                               .toProjectDTO(projectUpdater
                                               .patch(projectCode, projectMapper
                                                 .toProject(patchedProjectDTO))));
  }

  @Override
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        var projectList = projectService.getAll();
        return ResponseEntity.ok(projectMapper.toProjectsDTO(projectList));
    }

    @Override
    public ResponseEntity<ProjectDTO> addProject(ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectMapper
                .toProjectDTO(projectService
                        .create(projectMapper
                                .toProject(projectDTO))));
    }



}
