package com.aa.thrivetrack.models;
public class Streak {
    int streak_id;
    String streak_start;
    String streak_end;
    boolean streak_active;

    public Streak(String streak_start, String streak_end) {
        this.streak_start = streak_start;
        this.streak_end = streak_end;
    }

    public String getStreak_start() {
        return streak_start;
    }

    public void setStreak_start(String streak_start) {
        this.streak_start = streak_start;
    }

    public String getStreak_end() {
        return streak_end;
    }

    public void setStreak_end(String streak_end) {
        this.streak_end = streak_end;
    }

    @Override
    public String toString() {
        return "Streak{" +
                "streak_start='" + streak_start + '\'' +
                ", streak_end='" + streak_end + '\'' +
                '}';
    }

    public boolean isStreak_active() {
        return streak_active;
    }

    public void setStreak_active(boolean streak_active) {
        this.streak_active = streak_active;
    }

    public int getStreak_id() {
        return streak_id;
    }

    public void setStreak_id(int streak_id) {
        this.streak_id = streak_id;
    }
}
