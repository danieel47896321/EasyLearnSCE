package com.example.easylearnsce.Class;

public class Request {
    private String Id;
    private String Uid;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Type;
    private String Request;
    private String Details = "null";
    private String Status = "Pending Approval";
    private String Answer = "-";
    public Request() { }
    public Request(String uid, String firstName, String lastName, String email, String request, String type) {
        Id = (int)(Math.random()*100000000) + "";
        Uid = uid;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Request = request;
        Type = type;
    }
    public Request(String uid, String firstName, String lastName, String email, String request, String type, String details) {
        Id = (int)(Math.random()*100000000) + "";
        Uid = uid;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Request = request;
        Details = details;
        Type = type;
    }
    public String getUid() { return Uid; }
    public void setUid(String uid) { Uid = uid; }
    public String getType() { return Type; }
    public void setType(String type) { Type = type; }
    public String getAnswer() { return Answer; }
    public void setAnswer(String answer) { Answer = answer; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
    public String getId() { return Id; }
    public void setId(String id) { Id = id; }
    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }
    public String getRequest() { return Request; }
    public void setRequest(String request) { Request = request; }
    public String getDetails() { return Details; }
    public void setDetails(String details) { Details = details; }
}