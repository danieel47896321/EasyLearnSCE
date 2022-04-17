package com.example.easylearnsce.Client.Class;

import java.io.Serializable;

public class Topic implements Serializable {
    private String Topic;
    private String StartTime;
    private String EndTime;
    private Topic(){}
    public Topic(String topic, String startTime, String endTime) {
        Topic = topic;
        StartTime = startTime;
        EndTime = endTime;
    }
    public String getTopic() {
        return Topic;
    }
    public void setTopic(String topic) {
        Topic = topic;
    }
    public String getStartTime() {
        return StartTime;
    }
    public void setStartTime(String startTime) {
        StartTime = startTime;
    }
    public String getEndTime() {
        return EndTime;
    }
    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
