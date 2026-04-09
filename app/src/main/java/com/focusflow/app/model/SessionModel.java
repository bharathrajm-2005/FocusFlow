package com.focusflow.app.model;

public class SessionModel {
    private int duration;
    private long timestamp;

    public SessionModel(int duration, long timestamp) {
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
