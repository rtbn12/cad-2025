package com.example.demo;

public class Task {
    private static Long nextId = 0L;
    private final Long id;
    private String title;
    private String description;
    private boolean completed;

    public Task(String title, String description, boolean completed) {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public Task() {
        this.id = generateId();
    }

    private synchronized Long generateId (){return nextId++;}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Задача [ ID = " + id + " ;\n" +
                "        Название = " + title + " ;\n" +
                "        Описание = " + description + " ;\n" +
                "        Выполнено = " + (completed ? "Да" : "Нет") + " ]";
    }
}
