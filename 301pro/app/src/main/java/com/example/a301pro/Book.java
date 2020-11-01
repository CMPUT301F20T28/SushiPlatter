package com.example.a301pro;

import android.widget.ImageView;

import java.io.Serializable;

public class Book implements Serializable {
    private int imageID;
    private String book_name;
    private String author;
    private String ISBN;
    private String description;
    private String status;
    private String book_id;
    private String borrower_name;
    private User owner;

    public Book(int imageID, String book_name, String author, String ISBN, String description, String status, String book_id, String borrower_name, User owner) {
        this.imageID = imageID;
        this.book_name = book_name;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.status = status;
        this.book_id = book_id;
        this.borrower_name = borrower_name;
        this.owner = owner;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBorrower_name() {
        return borrower_name;
    }

    public void setBorrower_name(String borrower_name) {
        this.borrower_name = borrower_name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
