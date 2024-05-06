package com.aa.thrivetrack.models;
public class Streak {
    String streak_start;
    String streak_end;

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
}
