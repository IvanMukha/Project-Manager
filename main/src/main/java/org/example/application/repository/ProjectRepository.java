package org.example.application.repository;

import org.example.application.model.Project;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {
    List<Project> projects = new ArrayList<>();

    public void save(Project project) {
        projects.add(project);
    }

    public List<Project> getAll() {
        return new ArrayList<>(projects);
    }

    public Optional<Project> getById(int id) {
        return projects.stream().filter(project -> project.getId() == id).findFirst();
    }

    public void update(int id, Project updatedProject) {
        Optional<Project> optionalProject = getById(id);
        optionalProject.ifPresent(project -> project.setTitle(updatedProject.getTitle()).setDescription(updatedProject.getDescription()).setStatus(
                        updatedProject.getStatus()).setTeamId(updatedProject.getTeamId()).
                setManagerId(updatedProject.getManagerId()));
    }

    public void delete(int id) {
        projects.removeIf(project -> project.getId() == id);
    }
}
