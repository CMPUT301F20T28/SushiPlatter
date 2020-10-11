package com.example.a301pro;

import android.widget.ImageView;

public class Book {
    private int imageID;
    private String book_name;
    private String author;
    private String ISBN;
    private String description;
    private String status;
    private String Borrower_name;
    private String book_id;
    private User owner;

    public Book(int imageID, String book_name, String author, String ISBN, String description, String status, String borrower_name, String book_id, User owner) {
        this.imageID = imageID;
        this.book_name = book_name;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.status = status;
        this.Borrower_name = borrower_name;
        this.book_id = book_id;
        this.owner = owner;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getBorrower_name() {
        return Borrower_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getBook_id() { return book_id; }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBorrower_name(String borrower_name) {
        Borrower_name = borrower_name;
    }

    public void setBook_id(String book_id) { this.book_id = book_id; }
}
