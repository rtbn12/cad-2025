package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteTask(id);
    }

    public void updateTask(Long id, boolean completed) {
        taskRepository.updateTask(id, completed);
    }
}
