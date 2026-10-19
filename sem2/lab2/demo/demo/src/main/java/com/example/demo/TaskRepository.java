package com.example.demo;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private final List<Task> taskList = new ArrayList<>();

    public void addTask(Task task) {
        taskList.add(task);
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    public void deleteTask(Long id) {
        taskList.removeIf(task -> task.getId().equals(id));
    }


    public void updateTask(Long id, boolean completed) {
        Task foundTask = null;

        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                foundTask = task;
                break;
            }
        }

        if (foundTask != null) {
            taskList.remove(foundTask);
            foundTask.setCompleted(completed);

            if (completed) {
                taskList.add(foundTask);
            } else {
                taskList.add(0, foundTask);
            }
        }
    }
}
