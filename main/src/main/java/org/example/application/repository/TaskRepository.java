package org.example.application.repository;

import org.example.application.model.Task;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    List<Task> tasks = new ArrayList<>();

    public void save(Task task) {
        tasks.add(task);
    }

    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> getById(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst();
    }

    public void update(int id, Task updatedTask) {
        Optional<Task> optionalTask = getById(id);
        optionalTask.ifPresent(task -> task.setTitle(updatedTask.getTitle()).setStatus(updatedTask.getStatus()).setPriority(updatedTask.getPriority()).
                setDueDate(updatedTask.getDueDate()).setCategory(updatedTask.getCategory()).setLabel(updatedTask.getLabel()).
                setDescription(updatedTask.getDescription()));
    }

    public void delete(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }
}
