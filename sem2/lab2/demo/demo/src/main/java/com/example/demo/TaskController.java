package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("tasks", taskService.getAllTasks());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task){
        taskService.addTask(task);
        return "redirect:/";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask (@PathVariable Long id){
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam (defaultValue = "false") boolean completed){
        taskService.updateTask(id, completed);
        return "redirect:/";
    }

    @GetMapping("/addTaskPage")
    public String showAddTaskPage() {
        return "addTask";
    }


}
