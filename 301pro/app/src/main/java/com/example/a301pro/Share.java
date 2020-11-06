package com.example.a301pro;

import java.io.Serializable;

public class Share implements Serializable {
    private String bookID;
    private String imageId;
    private String book_name;
    private String des;
    private String sit;
    private String owner;

    public Share(String bookID,String imageId,String book_name, String des, String sit, String owner) {
        this.bookID = bookID;
        this.imageId =imageId;
        this.book_name = book_name;
        this.des = des;
        this.sit = sit;
        this.owner = owner;
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

    public String getOwner() {
        return owner;
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

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
