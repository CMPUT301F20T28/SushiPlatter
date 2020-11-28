package com.example.a301pro;

import com.google.firebase.firestore.GeoPoint;

/**
 * This class initializes the property of a borrowed book as well as gets the data of a book
 */
public class Borrowed {
    private String bookID;
    private String imageId;
    private String ISBN;
    private String bookName;
    private String des;
    private String oName;
    private String status;
    private GeoPoint location;
    /**
     * This constructor initializes the property of a borrowed book
     * @param imageId image id of the book
     * @param bookName name of the book
     * @param des description of the book
     * @param status status of the book
     * @param bookID id of the book
     * @param oName owner of the book
     */
    public Borrowed(String bookID, String imageId, String ISBN, String bookName, String des,
                    String status, String oName, GeoPoint location) {
        this.bookID = bookID;
        this.imageId = imageId;
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.des = des;
        this.status = status;
        this.oName = oName;
        this.location = location;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    /**
     * This allows the outside to get the ISBN code of a book
     * @return ISBN code of a book。
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * This set the ISBN code of a book
     * @param ISBN ISBN code to be set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * This allows the outside to get the owner's name of a book
     * @return ID of a book
     */
    public String getBookID() {
        return bookID;
    }
    /**
     * This set the image id of a book
     * @param bookID bookid to be set
     */
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    /**
     * This allows the outside to get the image id of a book
     * @return image id of a book
     */
    public String getImageId() {
        return imageId;
    }
    /**
     * This set the image id of a book
     * @param imageId image id to be set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    /**
     * This allows the outside to get the image id of a book
     * @return image id of a book
     */
    public String getBook_name() {
        return bookName;
    }
    /**
     * This allows the outside to get the description of a book
     * @return description of a book
     */
    public String getDes() {
        return des;
    }
    /**
     * This allows the outside to get the owner's name of a book
     * @return owner's name of a book
     */
    public String getO_name() {
        return oName;
    }
    /**
     * This allows the outside to get the status of a book
     * @return status of a book
     */
    public String getStatus() {
        return status;
    }
    /**
     * This set the description of a book
     * @param des description to be set
     */
    public void setDes(String des) {
        this.des = des;
    }
    /**
     * This set the description of a book
     * @param oName description to be set
     */
    public void setOwner(String oName) {
        this.oName = oName;
    }

    /**
     * This set the status of a book
     * @param status status to be set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * This set the name of a book
     * @param bookName name of a book to be set
     */
    public void setBook_name(String bookName) {
        this.bookName = bookName;
    }
}
