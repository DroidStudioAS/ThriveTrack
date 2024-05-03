package com.aa.thrivetrack.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Data {
    String goal;
    ArrayList<Task> tasks;
    User user;
    ArrayList<Diary> diary;

    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "goal='" + goal + '\'' +
                ", tasks=" +
                ", user=" + user.toString() +
                '}';
    }

    public Data(String goal, ArrayList<Task> tasks, User user, ArrayList<Diary> diary) {
        this.goal = goal;
        this.tasks = tasks;
        this.user = user;
        this.diary=diary;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Diary> getDiary() {
        return diary;
    }

    public void setDiary(ArrayList<Diary> diary) {
        this.diary = diary;
    }
}
