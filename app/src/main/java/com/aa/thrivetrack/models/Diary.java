package com.aa.thrivetrack.models;
public class Diary {
    int entry_id;
    int user_id;
    String entry_date;
    String entry_text;

    public Diary() {
    }

    public Diary(int entry_id, int user_id, String entry_date, String entry_text) {
        this.entry_id = entry_id;
        this.user_id = user_id;
        this.entry_date = entry_date;
        this.entry_text = entry_text;
    }

    public int getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getEntry_text() {
        return entry_text;
    }

    public void setEntry_text(String entry_text) {
        this.entry_text = entry_text;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "entry_id=" + entry_id +
                ", user_id=" + user_id +
                ", entry_date='" + entry_date + '\'' +
                ", entry_text='" + entry_text + '\'' +
                '}';
    }
}
