package com.aa.thrivetrack.models;

import java.util.Arrays;

public class Data {
    String goal;
    Task[] tasks;
    User user;

    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "goal='" + goal + '\'' +
                ", tasks=" + Arrays.toString(tasks) +
                ", user=" + user.toString() +
                '}';
    }

    public Data(String goal, Task[] tasks, User user) {
        this.goal = goal;
        this.tasks = tasks;
        this.user = user;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
