package com.example.a301pro;

import java.io.Serializable;

/**
 * This class initializes the property of a shareable book as well as gets the data of a book
 */
public class Share implements Serializable {
    private String bookID;
    private String imageId;
    private String ISBN;
    private String bookName;
    private String des;
    private String sit;
    private String owner;
    /**
     * This constructor initializes the property of a shareable book
     * @param imageId image id of the book
     * @param bookName name of the book
     * @param des description of the book
     * @param sit status of the book
     * @param bookID id of the book
     * @param owner owner of the book
     */
    public Share(String bookID,String imageId,String ISBN,String bookName, String des, String sit, String owner) {
        this.bookID = bookID;
        this.imageId =imageId;
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.des = des;
        this.sit = sit;
        this.owner = owner;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
    public String getSit() {
        return sit;
    }
    /**
     * This allows the outside to get the status of a book
     * @return status of a book
     */
    public String getOwner() {
        return owner;
    }
    /**
     * This set the description of a book
     * @param bookName description to be set
     */
    public void setBook_name(String bookName) {
        this.bookName = bookName;
    }
    /**
     * This set the description of a book
     * @param des description to be set
     */
    public void setDes(String des) {
        this.des = des;
    }
    /**
     * This set the status of a book
     * @param sit status to be set
     */
    public void setSit(String sit) {
        this.sit = sit;
    }
    /**
     * This set the name of a book
     * @param owner name of a book to be set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
