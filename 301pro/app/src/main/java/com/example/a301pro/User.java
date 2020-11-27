package com.example.a301pro;

import java.util.ArrayList;

/**
 * This class initializes the property of a user as well as gets the data of a user
 */
class User {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String UID;

    /**
     * This constructor initializes the property of a user
     * @param userName username of the user
     * @param email email of the user
     * @param password Login password of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param phoneNumber phone number of the user
     * @param UID unique UID of the user
     */
    public User(String userName, String email, String password, String firstName, String lastName,
                String phoneNumber, String UID) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.UID = UID;
    }

    public User() {
    }

    /**
     * This allows the outside to get the username of a user
     * @return username of a user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This set the username of a user
     * @param userName username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This allows the outside to get the email of a user
     * @return email of a user
     */
    public String getEmail() {
        return email;
    }

    /**
     * This set the email of a user
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This allows the outside to get the password of a user
     * @return password of a user
     */
    public String getPassword() {
        return password;
    }

    /**
     * This set the password of a user
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This allows the outside to get the first name of a user
     * @return first name of a user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This set the first name of a user
     * @param firstName first name to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This allows the outside to get the last name of a user
     * @return last name of a user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This set the last name of a user
     * @param lastName last name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This allows the outside to get the phone number of a user
     * @return phone number of a user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This set the phone number of a user
     * @param phoneNumber phone number to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This allows the outside to get the UID of a user
     * @return UID of a user
     */
    public String getUID() {
        return UID;
    }

}
