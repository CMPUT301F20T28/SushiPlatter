package com.example.a301pro;

import java.io.Serializable;

public class Share implements Serializable {
    private String bookID;
    private String imageId;
    private String book_name;
    private String des;
    private String sit;
    private String onwer;
    /**
     * This constructor initializes the property of a Share
     * @param imageId image id of the book
     * @param book_name name of the book
     * @param des description of the book
     * @param sit status of the book
     * @param bookID id of the book
     * @param onwer owner of the book
     */
    public Share(String bookID,String imageId,String book_name, String des, String sit, String onwer) {
        this.bookID = bookID;
        this.imageId =imageId;
        this.book_name = book_name;
        this.des = des;
        this.sit = sit;
        this.onwer = onwer;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getDes() {
        return des;
    }

    public String getSit() {
        return sit;
    }

    public String getOnwer() {
        return onwer;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setSit(String sit) {
        this.sit = sit;
    }

    public void setOnwer(String onwer) {
        this.onwer = onwer;
    }
}
