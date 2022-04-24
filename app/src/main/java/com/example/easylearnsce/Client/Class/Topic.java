package com.example.easylearnsce.Client.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable {
    private String Topic;
    private String StartTime;
    private String EndTime;
    private String Id;
    private ArrayList<YouTubeMessage> messages;
    private Topic(){
        messages = new ArrayList<>();
    }
    public Topic(String topic, String startTime, String endTime, String id) {
        messages = new ArrayList<>();
        Topic = topic;
        StartTime = startTime;
        EndTime = endTime;
        Id = id;
    }
    public ArrayList<YouTubeMessage> getMessages() {
        return messages;
    }
    public void setMessages(ArrayList<YouTubeMessage> messages) {
        this.messages = messages;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
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
