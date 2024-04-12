package com.ivan.projectManager.repository.impl;

import com.ivan.projectManager.model.Task;
import com.ivan.projectManager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private static final Logger log = LoggerFactory.getLogger(TaskRepositoryImpl.class);
    List<Task> tasks = new ArrayList<>();

    public List<Task> getAll() {
        return tasks;
    }

    public Task save(Task task) {
        tasks.add(task);
        return task;
    }

    public Optional<Task> getById(int id) {
        Optional<Task> optionalTask = tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
        if (optionalTask.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalTask;
    }


    public Optional<Task> update(int id, Task updatedTask) {
        Optional<Task> optionalTask = getById(id);
        optionalTask.ifPresent(task -> task.setTitle(updatedTask.getTitle()).setStatus(updatedTask.getStatus()).setPriority(updatedTask.getPriority()).
                setDueDate(updatedTask.getDueDate()).setCategory(updatedTask.getCategory()).setLabel(updatedTask.getLabel()).
                setDescription(updatedTask.getDescription()));
        return optionalTask;
    }

    public void delete(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }
}
