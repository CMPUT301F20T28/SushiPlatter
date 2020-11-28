package com.example.a301pro;
public class Message {
    private String timeStamp;
    private String timeMST;
    private String message;
    private String sender;
    private String receiver;

    public Message(String timeStamp, String timeMST, String message, String sender, String receiver) {
        this.timeStamp = timeStamp;
        this.timeMST = timeMST;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeMST() {
        return timeMST;
    }

    public void setTimeMST(String timeMSTT) {
        this.timeMST = timeMSTT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
