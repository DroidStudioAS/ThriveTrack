package com.aa.thrivetrack.models;
public class Task {
    private String task_text;

    public Task() {

    }
    public Task(String task_text) {
        this.task_text = task_text;
    }

    public String getTaskText() {
        return task_text;
    }

    @Override
    public String toString() {
        return task_text;
    }

    public void setTaskText(String task_text) {
        this.task_text = task_text;
    }
}
