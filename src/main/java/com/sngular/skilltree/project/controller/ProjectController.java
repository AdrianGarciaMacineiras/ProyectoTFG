package com.sngular.skilltree.project.controller;

import com.sngular.skilltree.api.ProjectApi;
import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProjectController implements ProjectApi {
    @Override
    public ResponseEntity<ProjectDTO> getProjectByCode(String projectcode) {
        return ProjectApi.super.getProjectByCode(projectcode);
    }

    @Override
    public ResponseEntity<Void> deleteProject(String projectcode) {
        return ProjectApi.super.deleteProject(projectcode);
    }

    @Override
    public ResponseEntity<ProjectDTO> updateProject(String projectcode, ProjectDTO projectDTO) {
        return ProjectApi.super.updateProject(projectcode, projectDTO);
    }

    @Override
    public ResponseEntity<ProjectDTO> patchProject(String projectcode, PatchedProjectDTO patchedProjectDTO) {
        return ProjectApi.super.patchProject(projectcode, patchedProjectDTO);
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        return ProjectApi.super.getProjects();
    }

    @Override
    public ResponseEntity<ProjectDTO> addProject(ProjectDTO projectDTO) {
        return ProjectApi.super.addProject(projectDTO);
    }



}
