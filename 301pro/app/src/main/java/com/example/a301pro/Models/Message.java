package com.example.a301pro.Models;

/**
 * This model initializes the property of a message notification
 */
public class Message {
    private String timeStamp;
    private String timeMST;
    private String message;
    private String sender;
    private String receiver;
    private String readStatus;
    private String messageNotificationStatus;

    /**
     * This constructor initializes the property of a message
     * @param timeStamp
     * @param timeMST
     * @param message
     * @param sender
     * @param receiver
     * @param readStatus
     * @param messageNotificationStatus
     */
    public Message(String timeStamp, String timeMST, String message, String sender, String receiver, String readStatus, String messageNotificationStatus) {
        this.timeStamp = timeStamp;
        this.timeMST = timeMST;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.readStatus = readStatus;
        this.messageNotificationStatus = messageNotificationStatus;

    }

    public String getMessageNotificationStatus() {
        return messageNotificationStatus;
    }

    public void setMessageNotificationStatus(String messageNotificationStatus) {
        this.messageNotificationStatus = messageNotificationStatus;
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


    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
