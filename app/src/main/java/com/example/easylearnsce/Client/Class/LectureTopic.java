package com.example.easylearnsce.Client.Class;

public class LectureTopic {
    private String Topic;
    private int From;
    private int To;
    public LectureTopic(String topic, int from, int to) {
        Topic = topic;
        From = from;
        To = to;
    }
    public String getTopic() {
        return Topic;
    }
    public void setTopic(String topic) {
        Topic = topic;
    }
    public int getFrom() {
        return From;
    }
    public void setFrom(int from) {
        From = from;
    }
    public int getTo() {
        return To;
    }
    public void setTo(int to) {
        To = to;
    }
}
