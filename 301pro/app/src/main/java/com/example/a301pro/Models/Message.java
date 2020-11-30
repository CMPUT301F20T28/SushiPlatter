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
     * @param timeStamp id of the message
     * @param timeMST local time in MST
     * @param message content of message
     * @param sender user who send notification
     * @param receiver user who receive the notification
     * @param readStatus status of message as read and unread
     * @param messageNotificationStatus status of notification
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

    /**
     * This allows outside to get the status of the message
     * @return status of the message
     */
    public String getMessageNotificationStatus() {
        return messageNotificationStatus;
    }

    /**
     * This allows outside to set the status of the message
     * @param messageNotificationStatus status of the message to be set
     */
    public void setMessageNotificationStatus(String messageNotificationStatus) {
        this.messageNotificationStatus = messageNotificationStatus;
    }

    /**
     * This allows outside to get the id of the message
     * @return id of the message
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * This allows outside to set the id of the message
     * @param timeStamp id of the message to be set
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * This allows outside to get the time of the message sent in MST
     * @return time of the message sent in MST
     */
    public String getTimeMST() {
        return timeMST;
    }

    /**
     * This allows outside to set the time of the message in MST
     * @param timeMSTT time of the message to be set
     */
    public void setTimeMST(String timeMSTT) {
        this.timeMST = timeMSTT;
    }

    /**
     * This allows outside to get the content of the message
     * @return content of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This allows outside to set the content of the message
     * @param message content of the message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This allows outside to get the name of the sender
     * @return name of the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * This allows outside to set the name of the sender
     * @param sender sender of the message to be set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * This allows outside to get the name of the receiver
     * @return name of the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * This allows outside to set the name of the sender
     * @param receiver name of the receiver to be set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * This allows outside to set the name of the sender
     * @return status of the read as read or unread
     */
    public String getReadStatus() {
        return readStatus;
    }

    /**
     * This allows outside to set the status of the read
     * @param readStatus set message as read or unread
     */
    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
