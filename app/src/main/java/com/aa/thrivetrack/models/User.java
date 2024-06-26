package com.aa.thrivetrack.models;

public class User {
    int user_id;
    String user_rank;
    int user_streak;


    public User() {
    }

    public User(int user_id, String user_rank) {
        this.user_id = user_id;
        this.user_rank = user_rank;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public int getUser_streak() {
        return user_streak;
    }

    public void setUser_streak(int user_streak) {
        this.user_streak = user_streak;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_rank='" + user_rank + '\'' +
                '}';
    }
}
